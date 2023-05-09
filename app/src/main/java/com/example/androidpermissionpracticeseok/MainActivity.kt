package com.example.androidpermissionpracticeseok

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private val cameraResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "카메라 권한 요청입니다.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "카메라 권한 요청입니다.", Toast.LENGTH_LONG).show()
            }
        }

    private val cameraAndLocationResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted) {
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                        Toast.makeText(this, "상세 위치 권한이 승인되었습니다.", Toast.LENGTH_LONG).show()
                    }else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(this, "대략 위치 권한이 승인되었습니다.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "카메라 권한이 승인되었습니다.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    when (permissionName) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            Toast.makeText(this, "상세 위치 권한이 거부되었습니다.", Toast.LENGTH_LONG).show()
                        }
                        Manifest.permission.ACCESS_COARSE_LOCATION -> {
                            Toast.makeText(this, "대략 위치 권한이 거부되었습니다.", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this, "카메라 권한이 거부되었습니다.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission: Button = findViewById<Button>(R.id.btnCameraPermission)
        btnCameraPermission.setOnClickListener {
            // shouldShowRequestPermissionRationale : 사용자가 한 번 거부해서 다시 한 번 요청할 기회가 남아 있을 때
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA
                )
            ) {
                showRationaleDialog("좀 더 정확한 설명", "제발 승인 해주세요 ㅠㅠ")
            } else {
                cameraAndLocationResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    )
                )
            }
        }
    }

    private fun showRationaleDialog(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title).setMessage(message).setPositiveButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()

    }
}

