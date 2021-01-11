package com.bhp.opusb.service.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.enumeration.CAttachmentType;
import com.bhp.opusb.domain.enumeration.CGalleryItemType;
import com.bhp.opusb.service.dto.marketplace.MProductCatalogAdaptableDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BhinnekaProductFeedDTO implements MProductCatalogAdaptableDTO {
  private boolean success;
  private int code;
  private String message;
  private List<BhinnekaProductDTO> data;
  private BhinnekaMetadataDTO meta;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<BhinnekaProductDTO> getData() {
    return data;
  }

  public void setData(List<BhinnekaProductDTO> data) {
    this.data = data;
  }

  public BhinnekaMetadataDTO getMeta() {
    return meta;
  }

  public void setMeta(BhinnekaMetadataDTO meta) {
    this.meta = meta;
  }

  public static class BhinnekaMetadataDTO {
    private int limit;
    private int page;
    private int totalPages;
    private int totalRecords;

    public int getLimit() {
      return limit;
    }

    public void setLimit(int limit) {
      this.limit = limit;
    }

    public int getPage() {
      return page;
    }

    public void setPage(int page) {
      this.page = page;
    }

    public int getTotalPages() {
      return totalPages;
    }

    public void setTotalPages(int totalPages) {
      this.totalPages = totalPages;
    }

    public int getTotalRecords() {
      return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
      this.totalRecords = totalRecords;
    }
  }

  @Override
  public Page<MProductCatalogDTO> map(Pageable page) {
    final String vendorName = "Bhinneka";
    List<MProductCatalogDTO> products = getData().stream()
      .map(product -> {
        final CGalleryDTO gallery = buildGallery(product);
        final MProductCatalogDTO dto = new MProductCatalogDTO();

        dto.setCGallery(gallery);
        dto.setMBrandName(product.getBrandName());
        dto.setName(product.getName());
        dto.setShortDescription(product.getDescription());
        dto.setPrice(product.getNormalPrice());
        dto.setLength(Double.valueOf(product.getRealLength()));
        dto.setWidth(Double.valueOf(product.getRealWidth()));
        dto.setHeight(Double.valueOf(product.getRealHeight()));
        dto.setWeight(Double.valueOf(product.getRealWeight()));
        dto.setCVendorName(vendorName);
        return dto;
      })
      .collect(Collectors.toList());

    return new PageImpl<>(products, page, getMeta().getTotalRecords());
  }

  private CGalleryDTO buildGallery(BhinnekaProductDTO product) {
    CGalleryDTO gallery = new CGalleryDTO();

    // Thumbnail.
    final CGalleryItemDTO itemPreview = new CGalleryItemDTO();
    final CAttachmentDTO thumbnail = new CAttachmentDTO();
    thumbnail.setType(CAttachmentType.REMOTE);
    thumbnail.setImageSmall(product.getThumbnailURL60x60());
    thumbnail.setImageMedium(product.getThumbnailURL160x160());
    itemPreview.setCAttachment(thumbnail);
    itemPreview.setSequence(0);
    itemPreview.setPreview(true);
    gallery.addCGalleryItem(itemPreview);

    // Carousel
    int sequence = 0;
    for (String url : product.getImageURLs500x500()) {
      final CGalleryItemDTO item = new CGalleryItemDTO();
      final CAttachmentDTO image = new CAttachmentDTO();
      image.setFileName(url);
      image.setType(CAttachmentType.REMOTE);
      item.setCAttachment(image);
      item.setSequence(++sequence);
      item.setType(CGalleryItemType.IMAGE);
      gallery.addCGalleryItem(item);
    }

    return gallery;
  }
}
