package com.example.movapp.ui.main
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mymovie.databinding.MainFragmentBinding

class MainFragment : Fragment() { 

    companion object {
        fun newInstance(bundle: Bundle?): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        
   arguments?.getParcelable<Movie>("MOVIE_INFO")?.let{ movie ->

         Log.d("DENIS", movie.release.toString())
        MovieLoader.loadWithRetro(movie.index, object:MovieLoader.OnMovieLoadListener{
                override fun onLoaded(webMovie: WebMovie) {
                binding.title.text = webMovie?.original_title
                binding.year.text = webMovie?.release_date
                binding.overView.text = webMovie?.overview
                binding.rank.text = webMovie?.vote_average.toString()

                   // viewModel.saveHistory(movie)
                }
                override fun onFailed(throwable: Throwable) {
                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
                }
            })
        }

        val geocoder : Geocoder = Geocoder(
            requireActivity().applicationContext)

        Thread { // тут я пробовал найти адрес по названию. То есть есть локации что имеют то же название что и фильмы. Их и хотел найти. Здесь неудача. 
            // если в качестве аргумента давать названия городов, то метод работает
            try {
                val address =  geocoder.getFromLocationName("${binding.title.text}", 1 )
                Log.d("DENIS", address.toString())
                /*requireActivity().runOnUiThread{
                    AlertDialog.Builder(requireActivity())
                        .setMessage("${address[0].getAddressLine(0)}")
                        .create()
                        .show()}*/
            } catch (e: Exception) {
            }
        }.start()

        binding.MapButton.setOnClickListener { // передаем название фильма во фрагмент с картой MapsFragment

            val message = binding.title.text
            val bundle = Bundle()
            bundle.putString("name", message as String?)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MapsFragment(bundle))
                .addToBackStack("")
                .commit()
        }
    }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
