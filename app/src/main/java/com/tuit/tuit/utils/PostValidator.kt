package com.tuit.tuit.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.tuit.tuit.R

object PostValidator {

    fun validatePost(
        titleProduct: String,
        descriptionProduct: String,
        priceText: String,
        region: Int,
        isPost: Boolean,
        categoryId: Int?,
        context: Context,
        imagesUrlList:List<Uri>
    ): Boolean{
        if (titleProduct.length < 4) {
            Toast.makeText(
                context,
                context.getString(R.string.errorTitle),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (categoryId == 0){
            Toast.makeText(context, context.getString(R.string.select_category), Toast.LENGTH_SHORT).show()
            return false
        }
        if (region == 0){
            Toast.makeText(context, context.getString(R.string.selectRegion), Toast.LENGTH_SHORT).show()
            return false
        }

        if (priceText.trim().isEmpty()) {
            Toast.makeText(context, context.getString(R.string.enter_amount), Toast.LENGTH_SHORT).show()
            return false
        }

        if (isPost) {
            if (imagesUrlList.isEmpty())
                return false
        }
        if (descriptionProduct.length < 9) {
            Toast.makeText(
                context,
                context.getString(R.string.description),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return true
    }

    fun isRegistrationValid(
        context: Context,
        textName: String,
        textEmail: String,
        textPassword: String,
        textReEnteredPassword: String,
    ): Boolean{
        if (textName.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.enterName), Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (!textEmail.isValidEmail()) {
            Toast.makeText(context, context.getString(R.string.enterValidEmail), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (textReEnteredPassword.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.enterName), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (textPassword.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.enterName), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (textReEnteredPassword != textPassword) {
            Toast.makeText(
                context,
                context.getString(R.string.passwordsMustBeEqual),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (textPassword.length<6){
            Toast.makeText(
                context,
                context.getString(R.string.passwordMustBeMoreThan6Char),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }



        return true
    }

}