package org.learn.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.learn.entity.Affiliate;
import org.learn.entity.Merchant;
import org.learn.repository.AffiliateRepository;
import org.learn.repository.MerchantRepository;
import org.learn.service.AdminService;

@ApplicationScoped
public class AdminServiceImpl implements AdminService {

    @Inject
    private MerchantRepository merchantRepository;
    @Inject
    private AffiliateRepository affiliateRepository;

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

    @Override
    @Transactional
    public void activatedAffiliate(String affiliateId) {
        Affiliate affiliate = affiliateRepository.findByIdOptional(affiliateId)
                .orElseThrow(() -> new WebApplicationException("User Affiliate Not Found", 404));

        if (!affiliate.getIsRequestAdmin()) {
            throw new WebApplicationException("User Affiliate Already Activated", 500);
        }

        affiliate.setIsRequestAdmin(false);
        affiliate.setIsActive(true);
        affiliateRepository.persist(affiliate);
    }
}
