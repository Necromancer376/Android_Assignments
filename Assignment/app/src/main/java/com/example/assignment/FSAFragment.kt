package com.example.assignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.components2.Restaurant
import com.example.assignment.R
import kotlinx.android.synthetic.main.fragment_fsa.rv_dashboard
import org.json.JSONArray
import org.json.JSONObject


class FSAFragment : Fragment() {

    lateinit var jsonData: String
    var list: ArrayList<Restaurant> = ArrayList<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fsa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_dashboard.layoutManager = LinearLayoutManager(requireContext())

        jsonData = requireContext().resources.openRawResource(
            requireContext().resources.getIdentifier(
                "restaurant_data", "raw", requireContext().packageName
            )
        ).bufferedReader().use { it.readText() }

        val outputJsonString = JSONObject(jsonData)

        val establishments = outputJsonString.getJSONObject("FHRSEstablishment")
            .getJSONArray("EstablishmentCollection") as JSONArray


        for (i in 0 until establishments.length()) {
            var geocode: JSONObject? = null
            if (establishments.getJSONObject(i).get("Geocode").toString() != "null")
                geocode = establishments.getJSONObject(i).getJSONObject("Geocode")
            var longitude: String = ""
            var latitude: String = ""


            var scores: JSONObject? = null
            if (establishments.getJSONObject(i).get("Scores").toString() != "null")
                scores = establishments.getJSONObject(i).getJSONObject("Scores")
            var hygine = "0"
            var structure = "0"
            var management = "0"

            if (geocode != null) {
                longitude = geocode.getString("Longitude")
                latitude = geocode.getString("Latitude")
//                Log.e("lat", latitude)
//                Log.e("lng", longitude)
            }

            if (scores != null) {
                hygine = scores.getString("Hygiene")
                structure = scores.getString("Structural")
                management = scores.getString("ConfidenceInManagement")
            }

            val res = Restaurant(
                establishments.getJSONObject(i).getString("BusinessName"),
                establishments.getJSONObject(i).getString("BusinessType"),
                establishments.getJSONObject(i).getString("RatingValue"),
                establishments.getJSONObject(i).getString("LocalAuthorityName"),
                establishments.getJSONObject(i).getString("LocalAuthorityEmailAddress"),
                arrayOf( hygine, structure, management ),
                establishments.getJSONObject(i).getString("LocalAuthorityWebSite"),
                longitude,
                latitude
            )
            list.add( res ) }

        val adapter = RVAdapter(list, requireContext(), requireActivity(), savedInstanceState)
        rv_dashboard.adapter = adapter
    }
}