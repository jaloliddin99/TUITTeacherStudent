package com.tuit.tuit.ui.student.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.tuit.tuit.databinding.FragmentProfileBinding
import com.tuit.tuit.utils.SharedPreferences


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var imagesRef: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val notificationsViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val storage = Firebase.storage("gs://daily-shopping-list-7bb71.appspot.com")
        val storageRef = storage.reference
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        imagesRef =
            storageRef.child("images").child(currentuser)

        if (SharedPreferences.getImageUrl(requireContext())!=""){
            Picasso.get().load(SharedPreferences.getImageUrl(requireContext())).into(binding.ivImage)
        }

        val textView: TextView = binding.fullname
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.ivProfile.setOnClickListener {
            openGalleryForImage()
        }

        binding.ivProfile.setOnClickListener {
            pickImageProfile()
        }
        return root
    }


    private fun pickImageProfile() {
        ImagePicker.with(this)
            .compress(1024)
            .crop()
            .galleryMimeTypes(
                mimeTypes = arrayOf("image/png", "image/jpg", "image/jpeg")
            )//Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!

                    val sharedPreferences = SharedPreferences
                    sharedPreferences.getProfileImage(requireContext(), fileUri)


                    binding.ivImage.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 111) {
            val uploadTask = imagesRef.putFile(data?.data!!)
            uploadTask(uploadTask)
        }
    }


    private fun uploadTask(uploadTask: UploadTask) {
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                    throw it
                }
            }
            imagesRef.downloadUrl

        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Picasso.get().load(downloadUri).into(binding.ivImage)
                SharedPreferences.saveImageUrl(requireContext(), task.result.toString())
            } else {
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}