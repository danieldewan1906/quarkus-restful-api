package org.learn.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.learn.entity.Merchant;
import org.learn.repository.MerchantRepository;
import org.learn.service.AdminService;

@ApplicationScoped
public class AdminServiceImpl implements AdminService {

    @Inject
    private MerchantRepository merchantRepository;

    @Override
    @Transactional
    public void activatedMerchant(String merchantId) {
        Merchant merchant = merchantRepository.findByIdOptional(merchantId)
                .orElseThrow(() -> new WebApplicationException("User Merchant Not Found", 404));

        if (!merchant.getIsRequestAdmin()) {
            throw new WebApplicationException("User Merchant Already Activated!", 500);
        }

        merchant.setIsRequestAdmin(false);
        merchant.setIsActive(true);
        merchantRepository.persist(merchant);
    }
}
