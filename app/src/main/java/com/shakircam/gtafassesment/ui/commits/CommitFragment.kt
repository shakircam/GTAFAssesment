package com.shakircam.gtafassesment.ui.commits

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakircam.gtafassesment.data.repository.GithubApiRepositoryImp
import com.shakircam.gtafassesment.databinding.FragmentCommitBinding
import com.shakircam.gtafassesment.model.Commits
import com.shakircam.gtafassesment.ui.viewmodel.GithubApiViewModel
import com.shakircam.gtafassesment.ui.viewmodel.GithubApiViewModelFactory
import com.shakircam.gtafassesment.utils.Resource


class CommitFragment : Fragment() {
    private var _binding: FragmentCommitBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { CommitAdapter() }

    private lateinit var githubApiViewModel: GithubApiViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommitBinding.inflate(inflater, container, false)
        val githubApiRepository = GithubApiRepositoryImp()
        val viewModelProviderFactory = GithubApiViewModelFactory(githubApiRepository,application = Application())
        githubApiViewModel = ViewModelProvider(this, viewModelProviderFactory).get(GithubApiViewModel::class.java)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        githubApiViewModel.commitsResponse.observe(viewLifecycleOwner){ response ->

            when(response){
                is Resource.Success -> {
                    response.data?.let { commitsResponse ->
                        adapter.setData(commitsResponse)
                    }
                }

                is Resource.Error -> {

                    response.message?.let { message ->
                        Log.e("tag", "An error occurred: $message")
                    }
                }

            }
        }

    }

    private fun startShimmerEffect() {
        //TODO("Not yet implemented")
    }

    private fun stopShimmerEffect() {
        //TODO("Not yet implemented")
    }

    private fun initRecyclerView() {
        val mRecyclerView = binding.recyclerView
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}