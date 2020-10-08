package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingSelectDisposalTypesBinding
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SelectDisposalTypesState
import ch.dreipol.multiplatform.reduxsample.view.SelectDisposalTypesAdapter

class SelectDisposalTypesFragment : OnboardingFragment() {

    private lateinit var selectDisposalTypesBinding: FragmentOnboardingSelectDisposalTypesBinding
    private lateinit var selectDisposalTypesAdapter: SelectDisposalTypesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        selectDisposalTypesBinding = viewBinding.fragmentOnboardingSelectDisposalTypes
        selectDisposalTypesBinding.root.visibility = View.VISIBLE
        selectDisposalTypesAdapter = SelectDisposalTypesAdapter(requireContext(), emptyMap())
        selectDisposalTypesBinding.disposalTypes.adapter = selectDisposalTypesAdapter
        return view
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        super.render(onboardingSubState)
        if (onboardingSubState !is SelectDisposalTypesState) return
        val data = mutableMapOf<DisposalType, Boolean>()
        data[DisposalType.CARTON] = onboardingSubState.showCarton
        data[DisposalType.BIO_WASTE] = onboardingSubState.showBioWaste
        data[DisposalType.PAPER] = onboardingSubState.showPaper
        data[DisposalType.E_TRAM] = onboardingSubState.showETram
        data[DisposalType.CARGO_TRAM] = onboardingSubState.showCargoTram
        data[DisposalType.TEXTILES] = onboardingSubState.showTextiles
        data[DisposalType.HAZARDOUS_WASTE] = onboardingSubState.showHazardousWaste
        data[DisposalType.SWEEPINGS] = onboardingSubState.showSweepings
        selectDisposalTypesAdapter.disposalTypes = data
        selectDisposalTypesAdapter.notifyDataSetChanged()
    }
}