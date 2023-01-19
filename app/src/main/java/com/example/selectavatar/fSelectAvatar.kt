package com.example.selectavatar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.selectavatar.databinding.FragmentFSelectAvatarBinding
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class fSelectAvatar : Fragment() {
    companion object {
        const val KEY = "key"
        const val NOMBRE_USUARIO = "nombreUsuario"
        const val IM_AVATAR = "imAvatarC"
        const val IM_FONDO = "imColorC"
    }

    lateinit var _binding : FragmentFSelectAvatarBinding
    val binding: FragmentFSelectAvatarBinding get() = _binding
    private val Nombres = mutableListOf<String>()

    private val adaptadorCol : AdaptadorCol = AdaptadorCol {color -> onItemSelectedColor(color)}
    private val adaptadorAva : AdaptadorAva = AdaptadorAva {avatar -> onItemSelectedAvatar(avatar)}
    var imAvatarC : Int = R.drawable.img_avatar_2
    var imColorC : Int = R.color.background_avatar_2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFSelectAvatarBinding.inflate(inflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crearRVAvatar()
        crearRVColor()

        binding.tvSugerirU.setOnClickListener {
            generarNombre()
        }

        binding.bListo.setOnClickListener {
            if ( !binding.etNombreU.text!!.isEmpty() ){
                val tNombre = binding.etNombreU.text.toString()
                setFragmentResult(KEY, bundleOf(
                    NOMBRE_USUARIO to tNombre,
                    IM_AVATAR to imAvatarC,
                    IM_FONDO to imColorC))
                findNavController().navigate(R.id.fSelectTofPerfil)
                binding.etNombreU.setText("")
            }else{
                Toast.makeText(activity,getString(R.string.notific_nombre), Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun onItemSelectedColor(color: AvatarColor){
        binding.ivAvatar.setCircleBackgroundColorResource(color.imagen)
        imColorC = color.imagen
    }
    fun onItemSelectedAvatar(avatar : AvatarColor){
        binding.ivAvatar.setImageResource(avatar.imagen)
        imAvatarC = avatar.imagen
    }
    fun crearRVAvatar(){
        binding.rvAvatar.setHasFixedSize(true)
        binding.rvAvatar.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adaptadorAva.AdaptadorAva(avatars())
        binding.rvAvatar.adapter = adaptadorAva
    }
    fun crearRVColor(){
        binding.rvColorFondo.setHasFixedSize(true)
        binding.rvColorFondo.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adaptadorCol.AdaptadorCol(colores())
        binding.rvColorFondo.adapter = adaptadorCol
    }
    private fun avatars(): MutableList<AvatarColor> {
        val avatares: MutableList<AvatarColor> = arrayListOf()

        avatares.add(AvatarColor(R.drawable.img_avatar_2))
        avatares.add(AvatarColor(R.drawable.img_avatar_1))
        avatares.add(AvatarColor(R.drawable.img_avatar_3))
        avatares.add(AvatarColor(R.drawable.img_avatar_4))
        avatares.add(AvatarColor(R.drawable.img_avatar_5))
        avatares.add(AvatarColor(R.drawable.img_avatar_6))

        return avatares
    }
    private fun colores(): MutableList<AvatarColor> {
        val colors: MutableList<AvatarColor> = arrayListOf()

        colors.add(AvatarColor(R.color.background_avatar_2))
        colors.add(AvatarColor(R.color.background_avatar_1))
        colors.add(AvatarColor(R.color.background_avatar_3))
        colors.add(AvatarColor(R.color.background_avatar_4))
        colors.add(AvatarColor(R.color.background_avatar_5))
        colors.add(AvatarColor(R.color.background_avatar_6))

        return colors
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://names.drycodes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun generarNombre(){
        viewLifecycleOwner.lifecycleScope.launch {
            val call: Response<List<String>> = getRetrofit().create(APIService::class.java).userRandom()
            val lNombres : List<String>? = call.body()
            if(call.isSuccessful){
                val nombres : List<String> = lNombres ?: emptyList()
                Nombres.clear()
                Nombres.addAll(nombres)
                binding.etNombreU.setText(Nombres[0])
            }else{
                Toast.makeText(activity,"Fallo",Toast.LENGTH_SHORT).show()
            }
        }
    }
}