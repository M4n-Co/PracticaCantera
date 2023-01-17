package com.example.selectavatar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.selectavatar.databinding.FragmentFPerfilBinding



class fPerfil : Fragment() {
    lateinit var _binding : FragmentFPerfilBinding
    val binding : FragmentFPerfilBinding get()=_binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(fSelectAvatar.KEY){requestKey: String, bundle: Bundle ->
            val nombre = bundle.getString(fSelectAvatar.NOMBRE_USUARIO)
            val avatar = bundle.getInt(fSelectAvatar.IM_AVATAR,0)
            val fondo = bundle.getInt(fSelectAvatar.IM_FONDO,0)

            val tBienvenida = getString(R.string.tituloSA) +" "+ nombre
            binding.tvTituloSA.text = tBienvenida
            binding.imAvatarC.setImageResource(avatar)
            binding.imAvatarC.setCircleBackgroundColorResource(fondo)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFPerfilBinding.inflate(inflater)
        val view = binding.root
        return view
    }

}