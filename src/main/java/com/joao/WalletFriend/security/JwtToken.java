package com.joao.WalletFriend.security;

public record JwtToken(
        String token,
        String type
) {
}
