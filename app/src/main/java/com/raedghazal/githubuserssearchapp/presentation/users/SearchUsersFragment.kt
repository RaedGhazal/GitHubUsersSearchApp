package com.raedghazal.githubuserssearchapp.presentation.users

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.raedghazal.githubuserssearchapp.App
import com.raedghazal.githubuserssearchapp.databinding.FragmentSearchUsersBinding
import com.raedghazal.githubuserssearchapp.di.viewmodels.ViewModelProviderFactory
import com.raedghazal.githubuserssearchapp.domain.model.User
import com.raedghazal.githubuserssearchapp.domain.usecase.GetUsersUseCase
import javax.inject.Inject

class SearchUsersFragment : Fragment(), UsersAdapter.OnItemClickListener {

    companion object {
        private const val ET_SEARCH_TEXT = "et_search_text"
        private const val DURATION_BEFORE_SEARCH = 800L
    }

    private var _binding: FragmentSearchUsersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchUsersBinding.inflate(inflater, container, false)

        return binding.root
    }

    @Inject
    lateinit var getUsersUseCase: GetUsersUseCase

    private val usersAdapter = UsersAdapter(this)

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private val viewModel: UsersViewModel by viewModels {
        viewModelProviderFactory
    }

    private val handler = Handler(Looper.getMainLooper())
    private var etSearchState = ""

    private var queryText = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val component = (activity?.application as App).appComponent
        component.inject(this)

//        viewModel.init()

        binding.rvSearchUsers.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }

        viewModel.users.observe(viewLifecycleOwner, {
            usersAdapter.submitData(lifecycle, it)
        })

        binding.pullToRefreshUsers.apply {
            setOnRefreshListener {
                usersAdapter.refresh()
                isRefreshing = false
            }
        }
        /**
         * Searches Users after [DURATION_BEFORE_SEARCH] milliseconds of not typing to reduce
         * the amount of server requests as Github allows only
         * [UsersPagingSource.MAX_REQUESTS_IN_PERIOD] requests per minute
         * */
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                handler.removeCallbacks(searchUsers)
            }

            override fun afterTextChanged(queryText: Editable?) {
                etSearchState = queryText.toString()
                if (queryText.toString().length >= 3) {
                    this@SearchUsersFragment.queryText = queryText.toString()
                    handler.postDelayed(searchUsers, DURATION_BEFORE_SEARCH)
                }
            }
        })

        /**
        usersAdapter.addLoadStateListener {
         * loadState.source.refresh is LoadState.Loading
        loadState.source.refresh is LoadState.NotLoading
        loadState.source.refresh is LoadState.Error
        loadState.source.refresh is LoadState.Error
        }
         */
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.etSearch.setText(savedInstanceState?.getString(ET_SEARCH_TEXT))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(ET_SEARCH_TEXT, etSearchState)
        super.onSaveInstanceState(outState)
    }

    val searchUsers = Runnable {
        viewModel.searchUsers(queryText)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(user: User) {
        val action =
            SearchUsersFragmentDirections.actionSearchUsersFragmentToUserDetailsFragment(user)
        findNavController().navigate(action)
    }


}
