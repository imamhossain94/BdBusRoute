package com.newagedevs.bdbusroute.fragments

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.utils.*

class MenuFragment : Fragment() {

    private lateinit var appVersion:TextView
    private lateinit var appDescription:TextView
    private lateinit var shareButton:Button
    private lateinit var otherAppButton:Button
    private lateinit var feedbackButton: Button
    private lateinit var contactButton: Button
    private lateinit var aboutDevelopment:Button
    private lateinit var rateAppButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_menu, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            appVersion = it.findViewById(R.id.app_version)
            appDescription = it.findViewById(R.id.apps_description)
            shareButton = it.findViewById(R.id.share_button)
            otherAppButton = it.findViewById(R.id.other_app_button)
            feedbackButton = it.findViewById(R.id.feedback_button)
            contactButton = it.findViewById(R.id.contact_button)
            aboutDevelopment = it.findViewById(R.id.app_dev_button)
            rateAppButton = it.findViewById(R.id.rating_button)
        }
        appVersion.text = getApplicationVersion()

        otherAppButton.setOnClickListener{
            openAppStore(requireActivity(), Constants.publisherName)
        }
        feedbackButton.setOnClickListener{
            openMailApp(requireActivity(), Constants.feedbackMail)
        }
        contactButton.setOnClickListener{
            openMailApp(requireActivity(), Constants.contactMail)
        }
        aboutDevelopment.setOnClickListener{
            showDevelopmentDialogue(requireActivity(), layoutInflater)
        }

        rateAppButton.setOnClickListener{
            showRatingDialogue(requireActivity(), layoutInflater)
        }
        shareButton.setOnClickListener{
            shareTheApp(requireActivity())
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appDescription.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }


    }


}