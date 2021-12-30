package com.example.movapp.ui.main
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mymovie.ListViewModel
import com.example.mymovie.databinding.MainFragmentBinding


class MainFragment : Fragment() { // Здесь рандомно будет выбираться фильм по id

    companion object {
        fun newInstance(bundle: Bundle?): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val repositorium: Repository = RepositoryImpl()

    private val listener = Repository.OnLoadListener{
        repositorium.showMovieFromServer()?.let { movie->
            binding.title.text = movie.title
            binding.year.text = movie.release
            binding.overView.text = movie.overview
            binding.rank.text = movie.rank.toString()
        }
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repositorium.addLoadListener(listener)

        val movie = arguments?.getParcelable<Movie>("MOVIE_INFO") // получили не вебмуви

        requireActivity().startService(
            Intent(requireContext(),IntentService::class.java).apply{
                putExtra("MOVIE_INFO", movie)
            }
        )
        //getMovieInfo()

         /*        MovieLoader.loadMovieFromWeb(550, object: MovieLoader.OnMovieLoadListener {
                    override fun onLoaded(webMovie: WebMovie)  {
                    binding.title.text = webMovie.original_title
                    binding.year.text = webMovie.release_date
                    binding.overView.text = webMovie.overview
                    binding.rank.text = webMovie.vote_average.toString()
                }
                override fun onFailed(throwable: Throwable) {
                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
                }
            })*/

    }

    private fun getMovie() : Movie? {
        val movie = arguments?.getParcelable<Movie>("MOVIE_INFO")
        return movie
    }

    private fun getMovieInfo() = with(binding) {
        arguments?.getParcelable<Movie>("MOVIE_INFO")?.let{ movie ->
            title.text = movie.title
            year.text = movie.release
            overView.text = movie.overview
            rank.text = movie.rank.toString()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        repositorium.removeLoadListener(listener)
        _binding = null
    }



}
