<template>
  <div class="product-detail">
    <el-row
      class="page-control"
      :gutter="24"
    >
      <el-col :span="6">
        <el-button
          class="btn-back"
          icon="el-icon-arrow-left"
          @click="goToPreviousPage"
        >
          Back
        </el-button>
      </el-col>
    </el-row>
    <el-row :gutter="24">
      <el-col :span="10">
        <div class="image-preview block" key="cover">
          <el-image class="image-main" fit="contain" :src="defaultImg" :preview-src-list="imgListsPreview" />
        </div>

        <el-row :gutter="5" type="flex" justify="center">
          <el-col :span="4" v-for="(img, i) in imgListsPreview" :key="i">
            <el-image class="image-thumbnail" fit="cover" @click="clickImageThumbnail(img)" :src="img" />
          </el-col>
        </el-row>
      </el-col>

      <el-col :span="14">
        <el-row style="margin-top: 10px">
          <el-col :span="24">
            <h3>
              <strong>{{ data.name }}</strong>
            </h3>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            {{ data.sku }}
          </el-col>
          <el-col :span="18">
            <el-rate v-model="rate" disabled show-score text-color="#ff9900" />
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <strong>Price</strong>
          </el-col>
          <el-col :span="18">
            <h1>
              <strong>Rp {{ data.price | formatCurrency('id') }}</strong>
            </h1>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <strong>Estimation</strong>
          </el-col>
          <el-col :span="18">
            {{ shipmentEstimation }}
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <strong>Quantity</strong>
          </el-col>
          <el-col :span="18">
            <el-input-number v-model="qty" @change="handleChangeQty" :min="1" size="mini" /> Total: Rp.
            {{ totalPrice | formatCurrency('id') }}
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <strong>Available Stock</strong>
          </el-col>
          <el-col :span="18">
            {{ data.stockAvailable }}
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <strong>Supplier</strong>
          </el-col>
          <el-col :span="18">
            {{ data.cVendorName }}
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6"> &nbsp; </el-col>
          <el-col :span="18">
            <el-button size="medium" type="primary" style="margin-left: 0px" @click="addToCart">
              <svg-icon name="shopping" /> Add to cart
            </el-button>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <strong>Warranty</strong>
          </el-col>
          <el-col :span="18">
            {{ data.warranty }}
          </el-col>
        </el-row>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24" class="column">
        <el-tabs class="set-border" v-model="activeTab" @tab-click="handleTabClick">
          <el-tab-pane
            v-for="tab in tabs"
            :key="tab.id"
            :label="tab.name"
            :name="tab.id"
          >
            <detail-description
              :content="tab.content"
              content-type="html"
              :title="tab.name"
            />
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" src="./marketplace-detail.component.ts">
</script>

<style lang="scss" scoped>
.product-detail {
  padding-top: 16px;

  .btn-back {
    margin-left: 10px;
  }
}
.image-preview {
  padding: 10px;

  .image-main {
    height: 400px;
    border: 1px dashed #d9d9d9;
    display: block;
    margin-left: auto;
    margin-right: auto;
  }
}

.image-thumbnail {
  cursor: pointer;
  width: 80px;
  height: 80px;
  border: 1px dashed #d9d9d9;
  padding: 5px;
}

.column {
  padding: 0px 10px 10px 10px;

  .set-border {
    border: 1px dashed #d9d9d9;
    padding: 5px;
  }
}

.el-row {
  margin-bottom: 15px;
  &:last-child {
    margin-bottom: 0;
  }
}
</style>
