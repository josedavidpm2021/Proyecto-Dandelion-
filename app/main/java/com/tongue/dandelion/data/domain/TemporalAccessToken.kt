package com.tongue.dandelion.data.domain

/** Token received from the backend to avoid customers from hacking the shipping fee **/
/** It should be sent back to the backend when the user finishes the checkout **/
/** It must not be modified **/

data class TemporalAccessToken(
    var expirationHour: Int,
    var expirationMinute: Int,
    var expirationSecond: Int,
    var base64Encoding: String
)
