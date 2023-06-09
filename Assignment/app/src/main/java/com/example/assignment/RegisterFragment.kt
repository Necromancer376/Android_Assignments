package com.example.assignment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.assignment.R
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.et_email
import kotlinx.android.synthetic.main.fragment_register.et_password
import java.io.File
import java.util.*


class RegisterFragment : BaseFragment(),
    BottomSheetImagePicker.OnImagesSelectedListener,
    DatePickerDialog.OnDateSetListener {

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        img_profile.setOnClickListener {
            pickImage()
        }

//        img_profile.setOnClickListener {
//            BottomSheetImagePicker.Builder(getString(R.string.file_provider))
//                .cameraButton(ButtonType.Button)            //style of the camera link (Button in header, Image tile, None)
//                .galleryButton(ButtonType.Button)           //style of the gallery link
//                .singleSelectTitle(R.string.pick_single)    //header text
//                .peekHeight(R.dimen.peekHeight)             //peek height of the bottom sheet
//                .columnSize(R.dimen.columnSize)             //size of the columns (will be changed a little to fit)
//                .requestTag("single")                       //tag can be used if multiple pickers are used
//                .show(requireActivity().supportFragmentManager)
//        }

        btn_register.setOnClickListener {
            if(et_email.text!!.isEmpty() || !et_email.text.toString().isValidEmail())
                showErrorSnackBar("Fill the Email", true)

            else if(et_password.text!!.length < 6)
                showErrorSnackBar("Fill Password", true)

            else if(et_confirm_password.text!!.length < 6)
                showErrorSnackBar("Fill Confirm Password", true)

            else if(et_username.text!!.length < 4)
                showErrorSnackBar("Fill Username", true)

            else if(tv_dob.text!!.isEmpty())
                showErrorSnackBar("Fill Date of Birth", true)

            else if(et_password.text.toString() != et_confirm_password.text.toString())
                showErrorSnackBar("Password and Confirm Password does not match", true)

            else {
                showErrorSnackBar("Registered Successfully", false)
            }
        }

        btn_dob.setOnClickListener {
            pickDate()
        }
        tv_dob.setOnClickListener {
            pickDate()
        }
    }

    private fun pickImage() {
        ImageBottomSheet(requireActivity())
            .show(requireActivity().supportFragmentManager, "newTaskTag")

//        var intent = Intent("android.provider.action.PICK_IMAGES")
//        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 101) {
                val uri = data?.data
                img_profile.setImageURI(uri)
            }
        }
    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        Log.e("uri", uris.toString())
        uris.forEach { uri ->
            Glide.with(requireContext()).load(uri).into(img_profile)
        }
    }

    fun pickDateCalander() {
        val cal = Calendar.getInstance()

        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONDAY)
        year = cal.get(Calendar.YEAR)
    }

    fun pickDate() {
        pickDateCalander()
        var datePicker = DatePickerDialog(requireContext(), R.style.MyTimePickerDialogTheme, this, year, month, day)
        datePicker.datePicker.maxDate= System.currentTimeMillis() - 568025136000L
        datePicker.show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        savedDay = p3
        savedMonth = p2
        savedYear = p1

        tv_dob.text = "DOB: " + "$savedDay/$savedMonth/$savedYear"
    }

    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}