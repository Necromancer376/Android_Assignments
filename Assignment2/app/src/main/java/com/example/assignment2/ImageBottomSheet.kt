package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_image_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.io.File

class ImageBottomSheet(val activity: Activity) : BottomSheetDialogFragment() {

    private lateinit var imageUri: Uri
    private val profile = activity.findViewById<ImageView>(R.id.img_nav_profile)

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        profile.setImageURI(null)
        profile.setImageURI(imageUri)
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageUri = createImageUri()!!

        btn_gallery.setOnClickListener {
            var intent = Intent("android.provider.action.PICK_IMAGES")
            startActivityForResult(intent, 101)
        }

        btn_camera.setOnClickListener {
            contract.launch(imageUri)
        }
    }

    private fun createImageUri(): Uri? {
        val image = File(requireActivity().applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(
            requireActivity().applicationContext,
            "com.malhar.assignment.fileProviderAssignment2",
            image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 101) {
                val uri = data?.data
                profile.setImageURI(uri)
                dismiss()
            }
        }
    }
}