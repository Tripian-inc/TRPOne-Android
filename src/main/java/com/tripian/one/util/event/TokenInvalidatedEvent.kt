package com.tripian.one.util.event

/**
 * Token tamamen gecersiz oldugunda post edilir.
 * TRPCore bu event'i dinleyip login ekranina yonlendirir.
 *
 * @param reason Token'in neden gecersiz oldugunu aciklayan mesaj
 */
class TokenInvalidatedEvent(val reason: String)
