<template>
    <div>
        <el-row :gutter="columnSpacing">
            <el-col :span="24">

                <el-button
                    type="danger" plain
                    size="mini"
                    icon="el-icon-close"
                    @click="back">
                    Back
                </el-button>

                <el-button
                    type="primary"
                    size="mini"
                    style="margin-left: 0px"
                    v-loading.fullscreen.lock="fullscreenLoading"
                    @click="validate">
                    Submit <em class="el-icon-arrow-right"></em>
                </el-button>

            </el-col>
        </el-row>

        <el-form ref="productCatalog" label-position="right" label-width="130px" size="mini" :model="productCatalog" :rules="rules">
            <el-divider content-position="left"><h4>Product Information</h4></el-divider>

            <el-row :gutter="columnSpacing">
                <el-col :span="24">
                    <el-row :gutter="columnSpacing">

                        <el-col :span="12">
                            <el-form-item label="Product" prop="mProductCategory" required>

                                <el-cascader
                                    clearable
                                    class="cascader"
                                    ref="productCategories"
                                    v-model="productCatalog.mProductCategory"
                                    :show-all-levels="true"
                                    :options="productCategoryOptions"
                                    :props="props"
                                    @change="setProductId"/>

                            </el-form-item>
                        </el-col>
                    </el-row>

                </el-col>
            </el-row>

            <el-row :gutter="columnSpacing">
                <el-col :span="12">

                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Name" prop="name" required>
                                <el-input class="form-input" clearable v-model="productCatalog.name" />
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">

                            <el-form-item label="Brand" prop="mBrandId" required>
                                <el-select
                                    style="width: 100%"
                                    v-model="productCatalog.mBrandId"
                                    class="form-input"
                                    clearable
                                    filterable
                                    :placeholder="$t('register.form.select')">
                                    <el-option
                                        v-for="item in brandOptions"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id" />
                                </el-select>
                            </el-form-item>

                        </el-col>
                    </el-row>

                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Short Desc." prop="shortDescription" required>
                                <el-input
                                    v-model="productCatalog.shortDescription"
                                    :autosize="{ maxRows: 4 }"
                                    class="form-input"
                                    clearable
                                    type="textarea"
                                />
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Additional Desc." prop="description">
                                <el-input
                                    v-model="productCatalog.description"
                                    :autosize="{ maxRows: 5 }"
                                    class="form-input"
                                    clearable
                                    type="textarea"
                                />
                            </el-form-item>
                        </el-col>
                    </el-row>
<!--
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">

                            <el-form-item label="Special Price" prop="specialPrice" required>
                                <special-price
                                    :specialPrices="productCatalog.specialPrices" />
                            </el-form-item>

                        </el-col>
                    </el-row>
-->
                </el-col>

                <!-- ============================================== -->

                <el-col :span="12">
                    <el-row :gutter="columnSpacing">
                        <el-col :span="12">
                            <el-form-item label="UoM" prop="cUomId" required>
                                <el-select
                                    style="width: 100%"
                                    v-model="productCatalog.cUomId"
                                    class="form-input"
                                    clearable
                                    filterable
                                    :placeholder="$t('register.form.select')"
                                >
                                    <el-option v-for="item in uomOptions" :key="item.id" :label="item.name" :value="item.id" />
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="Weight" prop="weight" required>
                                <el-input class="form-input" clearable v-model="productCatalog.weight" />
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Size" required>
                                <el-row :gutter="5" justify="space-between" type="flex">
                                    <el-col :span="8">
                                        <el-form-item prop="length" required>
                                            <el-input style="width: 100%" clearable v-model="productCatalog.length">
                                                <i slot="prefix" class="el-input__icon">L</i>
                                            </el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item prop="width" required>
                                            <el-input style="width: 100%" clearable v-model="productCatalog.width">
                                                <i slot="prefix" class="el-input__icon">W</i>
                                            </el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item prop="height" required>
                                            <el-input style="width: 100%" clearable v-model="productCatalog.height">
                                                <i slot="prefix" class="el-input__icon">H</i>
                                            </el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Expired Date" prop="expiredDate" required>
                                <el-date-picker
                                    style="width: 100%"
                                    class="form-input"
                                    clearable
                                    v-model="productCatalog.expiredDate"
                                    format="dd/MM/yyyy"
                                    value-format="yyyy-MM-dd"
                                    type="date"
                                    placeholder="Pick a day"
                                />
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row :gutter="columnSpacing">
                        <el-col :span="12">
                            <el-form-item label="Currency" prop="cCurrencyId" required>
                                <el-select
                                    style="width: 100%"
                                    v-model="productCatalog.cCurrencyId"
                                    class="form-input"
                                    clearable
                                    filterable
                                    :placeholder="$t('register.form.select')"
                                >
                                    <el-option
                                    v-for="item in currencyOptions"
                                    :key="item.id"
                                    :label="item.code"
                                    :value="item.id" />
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="Price" prop="price" required>
                                <el-input class="form-input" clearable v-model="productCatalog.price" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>

            <el-divider content-position="left"><h4>Image Information</h4></el-divider>

            <el-row :gutter="columnSpacing">
                <el-col :span="24">
                    <el-form-item label="Product Image" prop="images">

                        <product-images
                            :setImgCatalog="setRowProductCatalog.cGallery"
                            @galleryImage="setGalleryId" />

                    </el-form-item>
                </el-col>
            </el-row>

<!--
            <el-row :gutter="columnSpacing" style="margin-bottom: 20px">
                <el-col :span="24">
                    <el-form-item label="Product Video" prop="video" required>

                        <product-video
                            :productVideos="productInformation" />

                    </el-form-item>
                </el-col>
            </el-row>
-->
        </el-form>
    </div>
</template>

<script lang="ts" src="./product-information.component.ts"></script>

<style lang="scss">
.form-input {
  textarea {
    resize: none;
    border: none;
    border-radius: 0px;
    border-bottom: 1px solid #bfcbd9;
  }
}

.el-form-item is-required el-form-item--mini {
  .el-form-item__content {
    margin-left: 0px;
  }
}

.cascader {
    width: 100%;
}

</style>
