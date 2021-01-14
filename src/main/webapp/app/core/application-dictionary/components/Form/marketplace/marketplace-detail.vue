<template>
    <div class="product-detail">
        <el-row>
            <el-col :span="10">

                <div class="image-preview block" key="cover" >
                    <el-image
                        class="image-main"
                        fit="contain"
                        :src="defaultImg"
                        :preview-src-list="imgListsPreview"/>
                </div>

                <el-row :gutter="5" type="flex" justify="center">
                    <el-col :span="4" v-for="(img, i) in imgListsPreview" :key="i">
                        <el-image
                            class="image-thumbnail"
                            fit="cover"
                            @click="clickImageThumbnail(img)"
                            :src="img"/>
                    </el-col>
                </el-row>

            </el-col>

            <el-col :span="14">
                <el-row style="margin-top: 10px;">
                    <el-col :span="24">
                        <h3><strong>{{ data.name }}</strong></h3>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        {{ sku }}
                    </el-col>
                    <el-col :span="18">
                        <el-rate
                            v-model="rate"
                            disabled show-score
                            text-color="#ff9900"/>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <strong>Price</strong>
                    </el-col>
                    <el-col :span="18">
                        <h1><strong>Rp {{ data.price | formatCurrency('id') }}</strong></h1>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <strong>Credit</strong>
                    </el-col>
                    <el-col :span="18">
                        {{ cicilan }}
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <strong>Estimate</strong>
                    </el-col>
                    <el-col :span="18">
                        {{ estimate }}
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <strong>Quantity</strong>
                    </el-col>
                    <el-col :span="18">
                        <el-input-number v-model="qty" @change="handleChangeQty" :min="1" :max="availableStock" size="mini"/> Total: Rp. {{ totalPrice | formatCurrency('id') }}
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <strong>Available Stock</strong>
                    </el-col>
                    <el-col :span="18">
                        {{ availableStock }}
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <strong>Store Information</strong>
                    </el-col>
                    <el-col :span="18">
                        {{ storeInformation }}
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        &nbsp;
                    </el-col>
                    <el-col :span="18">
                        <el-button size="medium" type="warning" style="margin-left: 0px;" icon="el-icon-goods">
                            Pre Order
                        </el-button>
                        <el-button size="medium" type="primary" style="margin-left: 0px;" @click="addToCart">
                            <svg-icon name="shopping" /> Add to cart
                        </el-button>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <strong>Warranty</strong>
                    </el-col>
                    <el-col :span="18">
                        {{ warranty }}
                    </el-col>
                </el-row>

            </el-col>
        </el-row>

        <el-row>
            <el-col :span="24" class="column">

                <el-tabs
                    class="set-border"
                    v-model="activeName"
                    @tab-click="handleTabClick">

                    <el-tab-pane
                        v-for="item in tabTitleOptions"
                        :key="item.value"
                        :label="item.name"
                        :name="item.value">

                        <keep-alive>

                            <detail-description
                                v-if="activeName === item.value"
                                :detail-description="item"/>

                        </keep-alive>
                    </el-tab-pane>
                </el-tabs>

            </el-col>
        </el-row>

    </div>
</template>

<script lang="ts" src="./marketplace-detail.component.ts">
</script>

<style lang="scss" scoped>

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
