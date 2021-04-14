package com.raedghazal.githubuserssearchapp.presentation.users

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.raedghazal.githubuserssearchapp.data.model.UserResponse
import com.raedghazal.githubuserssearchapp.data.source.remote.RetrofitApi
import com.raedghazal.githubuserssearchapp.domain.model.User
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.lang.Integer.min
import java.util.concurrent.TimeUnit

private const val STARTING_PAGE_INDEX = 1
private const val LAST_PAGE_INDEX = 33

class UsersPagingSource(
    private val retrofitApi: RetrofitApi,
    private val queryText: String,
    private val context: Context
) : RxPagingSource<Int, User>() {

    companion object {
        private const val TAG = "UsersPagingSource"
        var totalNumberOfPages = 1

        const val MAX_REQUESTS_IN_PERIOD = 10
        private const val WAIT_BEFORE_NEXT_REQUEST = 61
        var startingSecond: Long = -1
        private val doneInLastMinute = mutableListOf<Long>()

        /**
         * This function calculates the duration of delay that need to be delayed before making
         * a request (if needed) instead of getting HTTP 403 exception if more than
         * [MAX_REQUESTS_IN_PERIOD] requests were made in less than [WAIT_BEFORE_NEXT_REQUEST]
         */
        fun calculateDelay(requestSystemTimeInSeconds: Long): Long {
            var requestTime = requestSystemTimeInSeconds
            if (startingSecond == -1L)
                startingSecond = requestTime

            requestTime -= startingSecond

            if (doneInLastMinute.size < MAX_REQUESTS_IN_PERIOD) {
                doneInLastMinute.add(requestTime)
            } else {
                val delay = (WAIT_BEFORE_NEXT_REQUEST) + doneInLastMinute[0] - requestTime
                doneInLastMinute.removeFirst()
                if (delay > 0) {
                    doneInLastMinute.add(requestTime + delay)
                    return delay
                } else {
                    doneInLastMinute.add(requestTime)
                }
            }
            return 0
        }
    }


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, User>> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX

        val delay = calculateDelay(System.currentTimeMillis() / 1000)
        Log.d(TAG, "loadSingle: delay =  $delay")
        if (delay > 0)
            Toast.makeText(
                context,
                "You've reached your limit please wait ${delay}sec to get the update",
                Toast.LENGTH_LONG
            ).show()


        return retrofitApi.getUsers(queryText, pageNumber)
            .delaySubscription(delay, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .doOnError {
                Log.d(TAG, "ERROR loadSingle: ${it.message} pages number = $totalNumberOfPages")
            }
            .map {
                if (pageNumber in 1..2)
                    Log.d(
                        TAG,
                        """loadSingle: 
                    {
                    result : success, 
                    query : $queryText
                    total_count : ${it.total_count},
                    pages : $totalNumberOfPages
                    }
                """.trimMargin()
                    )
                else
                    Log.d(TAG, "loadSingle: success : page number $pageNumber")

                totalNumberOfPages = min(LAST_PAGE_INDEX, it.total_count / 30)
                toLoadResult(it, pageNumber)
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    private fun toLoadResult(userResponse: UserResponse, pageNumber: Int): LoadResult<Int, User> =
        LoadResult.Page(
            data = userResponse.items,
            prevKey = if (pageNumber == STARTING_PAGE_INDEX) null
            else pageNumber - 1,
            nextKey = if (pageNumber >= totalNumberOfPages) null
            else pageNumber + 1,
        )

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
