package b172.challenging.auth.event.handler;

import b172.challenging.auth.event.RegisteredEvent;
import b172.challenging.member.domain.Member;
import b172.challenging.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisteredEventHandler {
    private final WalletService walletService;

    @EventListener
    public void createWallet(RegisteredEvent event) {
        Member member = event.getMember();
        walletService.createWallet(member);
    }
}
