package com.raedghazal.githubuserssearchapp.presentation.userdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.raedghazal.githubuserssearchapp.App
import com.raedghazal.githubuserssearchapp.R
import com.raedghazal.githubuserssearchapp.databinding.FragmentUserDetailsBinding


class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding: FragmentUserDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    private val args by navArgs<UserDetailsFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val component = (activity?.application as App).appComponent
        component.inject(this)

        binding.apply {
            val user = args.user

            Glide.with(this@UserDetailsFragment)
                .load(user.avatar_url)
                .circleCrop()
                .error(R.drawable.ic_error)
                .into(imgUserAvatar)

            tvUserName.text = user.login
            tvType.text = user.type

            btnRepo.setOnClickListener {
                launchBrowser(user.repos_url)
            }
            btnFollowing.setOnClickListener {
                val url : String? = user.following_url?.dropLast( "{/other_user}".length)?.trim()
                launchBrowser(url)
            }
            btnFollowers.setOnClickListener {
                launchBrowser(user.followers_url)
            }
        }
    }

    private fun launchBrowser(url: String?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}