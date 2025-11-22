package com.tripian.one

import android.content.Context
import com.tripian.one.api.bookings.TBookings
import com.tripian.one.api.bookings.model.ReservationRequest
import com.tripian.one.api.bookings.model.ReservationResponse
import com.tripian.one.api.bookings.model.ReservationsResponse
import com.tripian.one.api.cities.TCities
import com.tripian.one.api.cities.model.GetCitiesResponse
import com.tripian.one.api.cities.model.GetCityResponse
import com.tripian.one.api.companion.TCompanions
import com.tripian.one.api.companion.model.CompanionRequest
import com.tripian.one.api.companion.model.CompanionResponse
import com.tripian.one.api.companion.model.CompanionsResponse
import com.tripian.one.api.favorites.TFavorites
import com.tripian.one.api.favorites.model.FavoriteRequest
import com.tripian.one.api.favorites.model.FavoriteResponse
import com.tripian.one.api.favorites.model.FavoritesResponse
import com.tripian.one.api.misc.model.ConfigListResponse
import com.tripian.one.api.misc.model.TMisc
import com.tripian.one.api.offers.TOffers
import com.tripian.one.api.offers.model.AddOfferRequest
import com.tripian.one.api.offers.model.OfferResponse
import com.tripian.one.api.offers.model.OffersResponse
import com.tripian.one.api.pois.TPois
import com.tripian.one.api.pois.model.PoiCategoriesResponse
import com.tripian.one.api.pois.model.PoiResponse
import com.tripian.one.api.pois.model.PoisResponse
import com.tripian.one.api.reactions.TReactions
import com.tripian.one.api.reactions.model.ReactionRequest
import com.tripian.one.api.reactions.model.ReactionResponse
import com.tripian.one.api.reactions.model.ReactionsResponse
import com.tripian.one.api.trip.TTrips
import com.tripian.one.api.trip.model.AddCustomPoiStepRequest
import com.tripian.one.api.trip.model.AddStepRequest
import com.tripian.one.api.trip.model.DeleteResponse
import com.tripian.one.api.trip.model.ExportPlanRequest
import com.tripian.one.api.trip.model.ExportPlanResponse
import com.tripian.one.api.trip.model.PlanResponse
import com.tripian.one.api.trip.model.QuestionsResponse
import com.tripian.one.api.trip.model.StepAlternativesResponse
import com.tripian.one.api.trip.model.StepResponse
import com.tripian.one.api.trip.model.TripRequest
import com.tripian.one.api.trip.model.TripResponse
import com.tripian.one.api.trip.model.TripsResponse
import com.tripian.one.api.trip.model.UpdatePlanRequest
import com.tripian.one.api.trip.model.UpdateStepRequest
import com.tripian.one.api.trip.model.UpdateStepTimeRequest
import com.tripian.one.api.users.TUsers
import com.tripian.one.api.users.model.Device
import com.tripian.one.api.users.model.EmptyResponse
import com.tripian.one.api.users.model.ForgotPasswordRequest
import com.tripian.one.api.users.model.GuestLoginRequest
import com.tripian.one.api.users.model.LightLoginRequest
import com.tripian.one.api.users.model.LoginRequest
import com.tripian.one.api.users.model.LoginResponse
import com.tripian.one.api.users.model.RegisterRequest
import com.tripian.one.api.users.model.UpdateUserRequest
import com.tripian.one.api.users.model.UserResponse
import com.tripian.one.network.TConfig
import okhttp3.ResponseBody

class TRPRest(appContext: Context, url: String, key: String, device: Device) :
    TRPRestBase() {

    /**
     * Configs
     */
    init {
        TConfig.appContext = appContext
        TConfig.url = url
        TConfig.key = key
        TConfig.device = device
    }

    fun setPushToken(token: String?) {
        TConfig.device.serviceToken = token
    }

    fun setLanguage(lang: String) {
        TConfig.lang = lang
    }

    fun getLanguage(): String {
        return TConfig.lang
    }

    private val users: TUsers by lazy { TUsers() }
    private val trips: TTrips by lazy { TTrips() }
    private val companions: TCompanions by lazy { TCompanions() }
    private val cities: TCities by lazy { TCities() }
    private val pois: TPois by lazy { TPois() }
    private val reactions: TReactions by lazy { TReactions() }
    private val favorites: TFavorites by lazy { TFavorites() }
    private val bookings: TBookings by lazy { TBookings() }
    private val offers: TOffers by lazy { TOffers() }
    private val misc: TMisc by lazy { TMisc() }

    /**
     * region Users
     */
    fun login(
        request: LoginRequest,
        success: ((LoginResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = false) { users.login(request) }
    }

    fun register(
        request: RegisterRequest,
        success: ((LoginResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = false) { users.register(request) }
    }

    fun socialLogin(
        success: ((EmptyResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = false) { users.socialLogin() }
    }

    fun guestLogin(
        request: GuestLoginRequest,
        success: ((LoginResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = false) { users.guestLogin(request) }
    }

    fun lightLogin(
        request: LightLoginRequest,
        success: ((LoginResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = false) { users.lightLogin(request) }
    }

    fun logout(
        success: ((EmptyResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = true) { users.logout() }
    }

    fun sendMail(
        request: ForgotPasswordRequest,
        success: ((EmptyResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = false) { users.sendMail(request) }
    }

    fun resetPassword(
        request: ForgotPasswordRequest,
        success: ((EmptyResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = false) { users.resetPassword(request) }
    }

    fun getUser(success: ((UserResponse) -> Unit)? = null, error: ((Throwable?) -> Unit)? = null) {
        sendRequest(success, error) { users.getUser() }
    }

    fun updateUser(
        request: UpdateUserRequest,
        success: ((UserResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { users.updateUser(request) }
    }

    fun deleteUser(
        success: ((EmptyResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = true) { users.deleteUser() }
    }
    /** endregion */

    /**
     * region Trips
     */
    fun plan(
        planId: Int,
        success: ((PlanResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.plan(planId) }
    }

    fun exportPlan(
        request: ExportPlanRequest,
        success: ((ExportPlanResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.exportPlan(request) }
    }

    fun updatePlan(
        planId: Int,
        request: UpdatePlanRequest,
        success: ((PlanResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.updatePlan(planId, request) }
    }

    fun trips(
        dateFrom: String?, dateTo: String?, page: Int?, limit: Int?,
        success: ((TripsResponse) -> Unit)? = null, error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.trips(dateFrom, dateTo, page, limit) }
    }

    fun trip(
        tripHash: String,
        success: ((TripResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.trip(tripHash) }
    }

    fun createTrip(
        request: TripRequest,
        success: ((TripResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.createTrip(request) }
    }

    fun updateTrip(
        tripHash: String,
        request: TripRequest,
        success: ((TripResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.updateTrip(tripHash, request) }
    }

    fun deleteTrip(
        tripHash: String,
        success: ((DeleteResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.deleteTrip(tripHash) }
    }

    fun deleteStep(
        stepId: Int,
        success: ((DeleteResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.deleteStep(stepId) }
    }

    fun stepAlternatives(
        planId: Int,
        stepId: Int,
        tripHash: String,
        success: ((StepAlternativesResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.stepAlternatives(planId, stepId, tripHash) }
    }

    fun addStep(
        request: AddStepRequest,
        success: ((StepResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.addStep(request) }
    }

    fun addCustomPoiStep(
        request: AddCustomPoiStepRequest,
        success: ((StepResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.addCustomPoiStep(request) }
    }

    fun updateStep(
        stepId: Int,
        request: UpdateStepRequest,
        success: ((StepResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.updateStep(stepId, request) }
    }

    fun updateStepTime(
        stepId: Int,
        request: UpdateStepTimeRequest,
        success: ((StepResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.updateStepTime(stepId, request) }
    }

    fun questions(
        cityId: Int?,
        category: String?,
        languageCode: String?,
        success: ((QuestionsResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { trips.questions(cityId, category, languageCode) }
    }

    /** endregion */

    /**
     * region Companions
     */
    fun companions(
        page: Int?,
        limit: Int?,
        success: ((CompanionsResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { companions.companions(page, limit) }
    }

    fun addCompanion(
        request: CompanionRequest,
        success: ((CompanionResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { companions.addCompanion(request) }
    }

    fun updateCompanion(
        companionId: Int,
        request: CompanionRequest,
        success: ((CompanionResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { companions.updateCompanion(companionId, request) }
    }

    fun deleteCompanion(
        companionId: Int,
        success: ((DeleteResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { companions.deleteCompanion(companionId) }
    }
    /** endregion */

    /**
     * region Cities
     */
    fun cities(
        autoPagination: Boolean = false,
        search: String? = null,
        countryCode: String? = null,
        page: Int? = null,
        limit: Int? = null,
        success: ((GetCitiesResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest({
            if (autoPagination) {
                it.pagination?.let { paging ->
                    if (paging.currentPage != null && paging.totalPages != null) {
                        if (paging.currentPage < paging.totalPages) {
                            cities(
                                autoPagination,
                                search,
                                countryCode,
                                paging.currentPage + 1,
                                limit,
                                success,
                                error
                            )
                        }
                    }
                }
            }

            success?.invoke(it)
        }, error) { cities.cities(search, countryCode, page, limit) }
    }

    fun city(
        cityId: Int,
        success: ((GetCityResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { cities.city(cityId) }
    }

    /** endregion */

    /**
     * region Pois
     */
    fun getPoi(
        autoPagination: Boolean = false,
        cityId: Int? = null,
        search: String? = null,
        coordinate: Array<out String>? = null,
        poiIds: Array<out String>? = null,
        mustTryIds: Int? = null,
        categoryIds: Array<out Int>? = null,
        distance: Double? = null,
        boundary: String? = null,
        sort: String? = null,
        order: String? = null,
        price: String? = null,
        rating: Array<out Double>? = null,
        page: Int? = null,
        limit: Int? = null,
        success: ((PoisResponse) -> Unit)? = null, error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest({
            if (autoPagination) {
                it.pagination?.let { paging ->
                    if (paging.currentPage != null && paging.totalPages != null) {
                        if (paging.currentPage < paging.totalPages) {
                            getPoi(
                                autoPagination,
                                cityId,
                                search,
                                coordinate,
                                poiIds,
                                mustTryIds,
                                categoryIds,
                                distance,
                                boundary,
                                sort,
                                order,
                                price,
                                rating,
                                paging.currentPage + 1,
                                limit,
                                success,
                                error
                            )
                        }
                    }
                }
            }

            success?.invoke(it)
        }, error) {
            pois.getPoi(
                cityId,
                search,
                coordinate,
                poiIds,
                mustTryIds,
                categoryIds,
                distance,
                boundary,
                sort,
                order,
                price,
                rating,
                page,
                limit
            )
        }
    }

    fun getPoiDetail(
        poiId: String,
        success: ((PoiResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { pois.getPoiDetail(poiId) }
    }

    fun getPoiCategories(
        success: ((PoiCategoriesResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { pois.getPoiCategories() }
    }
    /** endregion */

    /**
     * region Reactions
     */
    fun addReaction(
        request: ReactionRequest,
        success: ((ReactionResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { reactions.addReaction(request) }
    }

    fun updateReaction(
        reactionId: Int,
        request: ReactionRequest,
        success: ((ReactionResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { reactions.updateReaction(reactionId, request) }
    }

    fun reactions(
        reaction: String? = null,
        tripHash: String? = null,
        page: Int? = null,
        limit: Int? = null,
        success: ((ReactionsResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { reactions.reactions(reaction, tripHash, page, limit) }
    }

    fun deleteReaction(
        reactionId: Int,
        success: ((DeleteResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { reactions.deleteReaction(reactionId) }
    }

    /** endregion */

    /**
     * region Favorites
     */
    fun addFavorite(
        request: FavoriteRequest,
        success: ((FavoriteResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { favorites.addFavorite(request) }
    }

    fun favorites(
        cityId: Int? = null,
        tripHash: String? = null,
        boundary: String? = null,
        page: Int? = null,
        limit: Int? = null,
        success: ((FavoritesResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { favorites.favorites(cityId, tripHash, boundary, page, limit) }
    }

    fun deleteFavorite(
        favoriteId: Int,
        success: ((DeleteResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { favorites.deleteFavorite(favoriteId) }
    }

    /** endregion */

    /**
     * region Bookings
     */
    fun bookings(
        provider: String? = null,
        tripHash: String? = null,
        cityId: Int? = null,
        poiId: String? = null,
        dateFrom: String? = null,
        dateTo: String? = null,
        page: Int? = null,
        limit: Int? = null,
        success: ((ReservationsResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) {
            bookings.bookings(
                provider,
                tripHash,
                cityId,
                poiId,
                dateFrom,
                dateTo,
                page,
                limit
            )
        }
    }

    fun addBookings(
        request: ReservationRequest,
        success: ((ReservationResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { bookings.addBookings(request) }
    }

    fun deleteBookings(
        bookingId: Int,
        success: ((DeleteResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { bookings.deleteBookings(bookingId) }
    }

    /** endregion */

    fun logout() {
        // todo: will developer
        TokenManager.clear()
    }

    /** endregion */

    /**
     * region offers
     */
    fun addOffer(
        offerId: Int,
        request: AddOfferRequest,
        success: ((OfferResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { offers.addOffer(offerId, request) }
    }

    fun getOffers(
        dateFrom: String? = null,
        dateTo: String? = null,
        poiIds: String? = null,
        typeId: String? = null,
        boundary: String? = null,
        excludeOptIn: Int? = null,
        success: ((OffersResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) {
            offers.getOffers(
                dateFrom,
                dateTo,
                poiIds,
                typeId,
                boundary,
                excludeOptIn
            )
        }
    }

    fun deleteOffer(
        offerId: Int,
        success: ((DeleteResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { offers.deleteOffer(offerId = offerId) }
    }

    fun getMyOffers(
        dateFrom: String? = null,
        dateTo: String? = null,
        success: ((PoisResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { offers.getMyOffers(dateFrom, dateTo) }
    }

    fun getPoisWithOffer(
        dateFrom: String? = null,
        dateTo: String? = null,
        boundary: String? = null,
        success: ((PoisResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error) { offers.getPoisWithOffer(dateFrom, dateTo, boundary) }
    }

    fun getLanguageValues(
        success: ((ResponseBody) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = false) {
            misc.getLanguages()
        }
    }

    fun getConfigList(
        success: ((ConfigListResponse) -> Unit)? = null,
        error: ((Throwable?) -> Unit)? = null
    ) {
        sendRequest(success, error, checkToken = false) {
            misc.getConfigList()
        }
    }
}