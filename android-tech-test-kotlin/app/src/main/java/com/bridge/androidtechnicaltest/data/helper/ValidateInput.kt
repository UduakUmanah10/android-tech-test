package com.bridge.androidtechnicaltest.data.helper


data class PupilInput(
    val country: String?,
    val name: String?,
    val image: String?,
    val latitude: Double?,
    val longitude: Double?
)

data class PupilValidationResult(
    val isCountryCorrect: Boolean = true,
    val isNameCorrect: Boolean = true,
    val isImageCorrect: Boolean = true,
    val isLatitudeCorrect: Boolean = true,
    val isLongitudeCorrect: Boolean = true
)

fun validatePupilInput(input: PupilInput): PupilValidationResult {
    return PupilValidationResult(
        isCountryCorrect = !input.country.isNullOrBlank() && input.country.length in 2..100,
        isNameCorrect = !input.name.isNullOrBlank() && input.name.length in 2..100,
        isImageCorrect = input.image.isNullOrBlank() || input.image.length in 11..1000,
        isLatitudeCorrect = input.latitude != null && input.latitude in -90.0..90.0,
        isLongitudeCorrect = input.longitude != null && input.longitude in -180.0..180.0
    )
}


fun isPupilInputValid(result: PupilValidationResult): Boolean {
    return result.isCountryCorrect &&
            result.isNameCorrect &&
            result.isImageCorrect &&
            result.isLatitudeCorrect &&
            result.isLongitudeCorrect
}
