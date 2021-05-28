<template>
  <div class="product-information">
    <h3>Supplier Catalog</h3>
    <el-form
      ref="mainForm"
      :label-position="formConfig.labelPosition"
      :label-width="formConfig.labelWidth"
      :model="mainForm"
      :rules="rules"
      :size="formConfig.size"
    >
      <el-row :gutter="24">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item
            label="Product"
            prop="mProductCategory"
          >
            <el-cascader
              v-model="mainForm.mProductCategory"
              clearable
              class="full-width"
              :show-all-levels="true"
              :options="productCategoryOptions"
              :props="props"
              @change="setProductId"
            ></el-cascader>
          </el-form-item>

          <el-form-item
            label="Name"
            prop="name"
          >
            <el-input
              v-model="mainForm.name"
              clearable
            ></el-input>
          </el-form-item>

          <el-form-item
            label="Brand"
            prop="mBrandId"
          >
            <el-select
              v-model="mainForm.mBrandId"
              class="full-width"
              clearable
              filterable
            >
              <el-option
                v-for="item in brandOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item
            label="Short Description"
            prop="shortDescription"
          >
            <el-input
              v-model="mainForm.shortDescription"
              :autosize="{ maxRows: 3 }"
              clearable
              type="textarea"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="Additional Description"
            prop="description"
          >
            <el-input
              v-model="mainForm.description"
              :autosize="{ maxRows: 5 }"
              clearable
              type="textarea"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="UoM"
            prop="cUomId"
          >
            <el-select
              v-model="mainForm.cUomId"
              class="full-width"
              clearable
              filterable
            >
              <el-option
                v-for="item in uomOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item
            label="Weight"
            prop="weight"
          >
            <el-input-number
              v-model="mainForm.weight"
              controls-position="right"
              :precision="2"
              :step="0.1"
            ></el-input-number>
          </el-form-item>

          <el-form-item label="Size">
            <el-row :gutter="5" justify="space-between" type="flex">
              <el-col :span="8">
                <el-input-number
                  v-model="mainForm.length"
                  controls-position="right"
                  :precision="2"
                  :step="0.1"
                >
                  <em slot="prefix" class="el-input__icon">L</em>
                </el-input-number>
              </el-col>
              <el-col :span="8">
                <el-input-number
                  v-model="mainForm.width"
                  controls-position="right"
                  :precision="2"
                  :step="0.1"
                >
                  <em slot="prefix" class="el-input__icon">W</em>
                </el-input-number>
              </el-col>
              <el-col :span="8">
                <el-input-number
                  v-model="mainForm.height"
                  controls-position="right"
                  :precision="2"
                  :step="0.1"
                >
                  <em slot="prefix" class="el-input__icon">H</em>
                </el-input-number>
              </el-col>
            </el-row>
          </el-form-item>

          <el-form-item
            label="Currency"
            prop="cCurrencyId"
          >
            <el-select
              v-model="mainForm.cCurrencyId"
              class="full-width"
              clearable
              filterable
            >
              <el-option
                v-for="item in currencyOptions"
                :key="item.id"
                :label="item.code"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item
            label="Price"
            prop="price"
          >
            <el-input
              v-model="mainForm.price"
              v-inputmask="{ alias: 'currency' }"
              clearable
            ></el-input>
          </el-form-item>
          
          <el-form-item
            label="Expired Date"
            prop="expiredDate"
          >
            <el-date-picker
              v-model="mainForm.expiredDate"
              class="full-width"
              clearable
              :format="dateDisplayFormat"
              type="date"
              :value-format="dateValueFormat"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider content-position="left"><h4>Image Information</h4></el-divider>

      <el-row :gutter="columnSpacing">
        <el-col :span="24">
          <el-form-item label="Product Image" prop="images">
            <product-images
              v-model="mainForm.cGalleryId"
              :gallery-name="galleryName"
            ></product-images>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script lang="ts" src="./product-information.component.ts"></script>

<style lang="scss">
.product-information {
  .full-width {
    width: 100%;
  }
}
</style>
