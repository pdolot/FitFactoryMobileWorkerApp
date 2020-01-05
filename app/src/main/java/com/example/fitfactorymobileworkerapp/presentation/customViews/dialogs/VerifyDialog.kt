package com.example.fitfactorymobileworkerapp.presentation.customViews.dialogs

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.api.Pass
import kotlinx.android.synthetic.main.view_verify_dialog.view.*

class VerifyDialog @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var pass: Pass? = null
        set(value) {
            field = value
            field?.let {
                bindData()
            }
        }

    var onClose: () -> Unit = {}
    var onVerify: (Long) -> Unit = {}
    var onGiveKey: (Long) -> Unit = {}

    private fun bindData() {
        pass?.let {
            user.text = "${it.passUser?.firstName} ${it.passUser?.lastName}"
            birthDate.text = it.passUser?.birthDate
            identityNumber.text = it.passUser?.identityNumber

            if (it.active == false){
                verify.visibility = View.GONE
                giveKey.visibility = View.GONE
                statusIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pass_non_active))
            }

            if (it.verified == true){
                setStateVerified()
            }else{
                giveKey.isEnabled = false
                verify.isEnabled = true
                verify.text = "WERYFIKUJ"
                statusIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pass_not_verified))
            }
        }
    }

    init {
        View.inflate(context, R.layout.view_verify_dialog, this)
        close.setOnClickListener { onClose() }
        verify.setOnClickListener { onVerify(pass?.passId ?: 0) }
        giveKey.setOnClickListener { onGiveKey(pass?.passUser?.id ?: 0) }
    }

    fun setStateVerified(){
        verify.isEnabled = false
        verify.text = "Zweryfikowano"
        giveKey.isEnabled = true
        statusIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pass_verified))
    }

}