
package com.ulta.core.bean.search;

import com.ulta.core.bean.UltaBean;

public class SearchResultsBean extends UltaBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5513439453256109668L;
	private String badgeImgURL;
	private String badgeName;
	private String brandName;
	private CreationDateBean creationDate;
	private String displayName;
	private String hasSkusOnSale;
	private String id;
	private int isGWP;
	private double listPriceFrom;
	private double listPriceTo;
	private double rating;
	private double reviews;
	private double salePriceFrom;
	private double salePriceTo;
	private String smallImageUrl;
	private String mediumImageUrl;
	private String giftUrl;
	private String promotionId;
	private String promoOfferDate;
	private String promoDescription;
	private String promoOfferLink;
	
	
	public String getPromoOfferLink() {
		return promoOfferLink;
	}

	public void setPromoOfferLink(String promoOfferLink) {
		this.promoOfferLink = promoOfferLink;
	}

	public String getPromoOfferDate() {
		return promoOfferDate;
	}

	public void setPromoOfferDate(String promoOfferDate) {
		this.promoOfferDate = promoOfferDate;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	private String offerType;
	private String offerDesc;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getHasSkusOnSale() {
		return hasSkusOnSale;
	}

	public void setHasSkusOnSale(String hasSkusOnSale) {
		this.hasSkusOnSale = hasSkusOnSale;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIsGWP() {
		return isGWP;
	}

	public void setIsGWP(int isGWP) {
		this.isGWP = isGWP;
	}

	public double getListPriceFrom() {
		return listPriceFrom;
	}

	public void setListPriceFrom(double listPriceFrom) {
		this.listPriceFrom = listPriceFrom;
	}

	public double getListPriceTo() {
		return listPriceTo;
	}

	public void setListPriceTo(double listPriceTo) {
		this.listPriceTo = listPriceTo;
	}

	public double getReviews() {
		return reviews;
	}

	public void setReviews(double d) {
		this.reviews = d;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getSalePriceFrom() {
		return salePriceFrom;
	}

	public void setSalePriceFrom(double salePriceFrom) {
		this.salePriceFrom = salePriceFrom;
	}

	public double getSalePriceTo() {
		return salePriceTo;
	}

	public void setSalePriceTo(double salePriceTo) {
		this.salePriceTo = salePriceTo;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}
	
	public String getMediumImageUrl() {
		return mediumImageUrl;
	}

	public void setMediumImageUrl(String mediumImageUrl) {
		this.mediumImageUrl = mediumImageUrl;
	}

	public CreationDateBean getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(CreationDateBean creationDate) {
		this.creationDate = creationDate;
	}

	public String getGiftUrl() {
		return giftUrl;
	}

	public void setGiftUrl(String giftUrl) {
		this.giftUrl = giftUrl;
	}

	public String getBadgeImgURL() {
		return badgeImgURL;
	}

	public void setBadgeImgURL(String badgeImgURL) {
		this.badgeImgURL = badgeImgURL;
	}

	public String getBadgeName() {
		return badgeName;
	}

	public void setBadgeName(String badgeName) {
		this.badgeName = badgeName;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getOfferDesc() {
		return offerDesc;
	}

	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}
}
