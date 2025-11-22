package com.tripian.one.api.misc.model


import java.io.Serializable

class ConfigList : Serializable {
    val androidVersionCode: Int? = null
    val androidVersionName: String? = null
    val apiKey: String? = null
    val baseFeatures: BaseFeatures? = null
    val providers: Providers? = null
    val sbt: Sbt? = null
    val whiteLabels: WhiteLabels? = null
}

class BaseFeatures : Serializable {
    val enableCustomEventCreation: Boolean = false
    val enableCustomPoiCreation: Boolean = false
    val loginWithHash: Boolean = false
    val loginWithToken: Boolean = false
    val qrReader:Boolean = false
    val saveSession: Boolean = false
    val sharedTrip: Boolean = false
    val showAccommodationPois: Boolean = false
    val showChangePassword: Boolean = false
    val showCreateTrip: Boolean = false
    val showCustomPoiResults: Boolean = false
    val showHome: Boolean = false
    val showLogin: Boolean = false
    val showOffers: Boolean = false
    val showOverview: Boolean = false
    val showRegister: Boolean = false
    val showSaveTrip: Boolean = false
    val showSideNav: Boolean = false
    val showStepCardThumbs: Boolean = false
    val showStepScoreDetail: Boolean = false
    val showTravelGuide: Boolean = false
    val showTripModeQuestion: Boolean = false
    val showUpdateTrip: Boolean = false
    val showUserProfile: Boolean = false
    val showVoucher: Boolean = false
    val widgetTheme1: Boolean = false
}


class Cognito: Serializable{
    val clientId: String? = null
    val domain: String? = null
    val identityProviders: List<String?>? = null
    val region: String? = null
}


class ThemeInner: Serializable {
    val background: String? = null
    val danger: String? = null
    val headerBg: String? = null
    val headerTextColor: String? = null
    val info: String? = null
    val primary: String? = null
    val secondary: String? = null
    val success: String? = null
    val textPrimary: String? = null
    val warning: String? = null
}

class GoogleAnalytics: Serializable {
    val business: String? = null
    val customer: String? = null
}

class ImagePaths: Serializable {
    val appBackgroundImgUrl: String? = null
    val formBgImgUrl: String? = null
    val formHeaderImgUrl: String? = null
    val logoPathDark: String? = null
    val logoPathLight: String? = null
}

class Providers: Serializable {
    val accommodation: List<ProviderInner?>? = null
    val restaurant: List<ProviderInner?>? = null
    val tourAndTicket: List<ProviderInner?>? = null
    val transportation: List<ProviderInner?>? = null
}

class ProviderInner: Serializable {
    val apiKey: String? = null
    val apiUrl: String? = null
    val clientId: String? = null
    val id: Int? = null
    val name: String? = null
    val prod: Boolean? = null
}

class Sbt: Serializable {
    val campaignDefaultCurrency: String? = null
    val offerTypes: List<String?>? = null
}

class Theme: Serializable {
    val dark: ThemeInner? = null
    val light: ThemeInner? = null
}

class WhiteLabels: Serializable {
    val availableThemes: List<String?>? = null
    val brandName: String? = null
    val brandUrl: String? = null
    val cognito: Cognito? = null
    val dealsPageUrl: String? = null
    val defaultDestinationId: Int? = null
    val externalMenuLinks: ExternalMenuLinks? = null
    val googleAnalyticsKey: GoogleAnalytics? = null
    val googleAnalyticsUrl: GoogleAnalytics? = null
    val imagePaths: ImagePaths? = null
    val landingPageUrl: String? = null
    val languages: List<Any?>? = null
    val ppUrl: String? = null
    val providers: List<Any?>? = null
    val reverseProxyUrl: String? = null
    val rootPath: String? = null
    val theme: Theme? = null
    val tosUrl: String? = null
}

class ExternalMenuLinks: Serializable {
    val home: List<Home?>? = null
}

class Home: Serializable {
    val url: String? = null
    val name: String? = null
    val externalLink: Boolean? = null
}

