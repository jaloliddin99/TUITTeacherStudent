package com.tuit.tuit.ui.student.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tuit.tuit.R
import com.tuit.tuit.databinding.FragmentHomeBinding
import com.tuit.tuit.repository.model.Data
import com.tuit.tuit.ui.student.adapter.FileAdapter

class HomeFragment : Fragment(), FileAdapter.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var databaseReference: DatabaseReference
    private val list = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.adapter = FileAdapter(recyclerFiletList(), this@HomeFragment)
            recycler.adapter = FileAdapter(recyclerFiletList(), this@HomeFragment)

        }

        databaseReference = Firebase.database.reference.child("subjects").child("Tarmoq_havfsizligi")


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val data = snap.getValue(Data::class.java)
                    list.add(data!!)
                    Log.d("data", "onDataChange:${data} ")
                }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }


    private fun recyclerFiletList(): ArrayList<Data> {

        return list
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked() {
        findNavController().navigate(R.id.action_navigation_home_to_openFileFragment)
    }

    var subjectName = ""
}


