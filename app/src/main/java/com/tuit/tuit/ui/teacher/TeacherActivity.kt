package com.tuit.tuit.ui.teacher

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tuit.tuit.R
import com.tuit.tuit.databinding.ActivityLoginBinding
import com.tuit.tuit.databinding.ActivityTeacherBinding
import com.tuit.tuit.repository.model.UploadFile
import com.tuit.tuit.ui.teacher.dialog.SelectSubjectDialog
import com.tuit.tuit.utils.PostValidator

class TeacherActivity : AppCompatActivity(), SelectSubjectDialog.OnItemConfirmed {

    private var fileUri: Uri? = null

    var firebaseAuth: FirebaseAuth? = null
    var firebaseDatabase: DatabaseReference? = null


    private lateinit var binding: ActivityTeacherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()



        binding.apply {
            selectSubject.setOnClickListener {
                SelectSubjectDialog(this@TeacherActivity).show(supportFragmentManager, "tag")
            }

            send.setOnClickListener {
                val title = binding.title.text.toString().trim()
                val description = binding.description.text.toString().trim()
                val subject = binding.selectSubject.text.toString().trim()
                val fileUri = fileUri

                if (PostValidator.validatePost(
                        title = title,
                        description = description,
                        subjectName = subject,
                        fileUri = fileUri,
                        context = this@TeacherActivity
                    )
                ) {

                    firebaseDatabase =
                        FirebaseDatabase.getInstance().reference.child("subjects").child(subject)
                    firebaseDatabase!!.keepSynced(true)

                    val key = firebaseDatabase?.push()?.key!!
                    val databaseReference: DatabaseReference? = firebaseDatabase?.child(key)

                    databaseReference?.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val uploadFile=UploadFile(
                                subjectName = subject,
                                description = description,
                                fileTitle = title,
                                url = ""
                            )
                            databaseReference.setValue(uploadFile)

                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })

                }

            }
        }
    }

    override fun onItemConfirmed(name: String) {
        binding.selectSubject.text = name
    }
}