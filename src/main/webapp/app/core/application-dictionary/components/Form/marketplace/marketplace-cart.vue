<template>
    <div class="app-container">

        <el-row>
            <el-col :span="24">
                <h4><b>Shopping cart</b></h4>
            </el-col>
        </el-row>

        <el-row>
            <el-col :span="12">
                <el-checkbox v-model="checked">Select All</el-checkbox>
            </el-col>

            <el-col :span="12" align="end">
                <el-button type="danger" icon="el-icon-delete" plain>Remove All</el-button>
            </el-col>
        </el-row>

        <el-row class="row" v-for="itemCart in data" :key="itemCart.storeId">
            <el-col :span="24" class="set-border">

                <el-row>
                    <el-col :span="24">
                        <el-checkbox v-model="checked"><i class="el-icon-s-shop"></i> {{ itemCart.store }}</el-checkbox>
                    </el-col>
                </el-row>

                <el-row class="itemDetail" v-for="itemDetail in itemCart.item" :key="itemDetail.itemId">
                    <el-col :span="1">
                        <el-checkbox v-model="checked"/>
                    </el-col>
                    <el-col :span="2">
                        <el-image
                            class="image-thumbnail getDetail"
                            fit="contain"
                            :src="itemDetail.img"
                            @click="getDetail"/>
                    </el-col>

                    <el-col :span="15">

                        <el-row>
                            <el-col :span="24">
                                <b class="getDetail" @click="getDetail">{{ itemDetail.name }}</b>
                            </el-col>
                            <el-col :span="24">
                                {{ itemDetail.price }}
                            </el-col>
                            <el-col :span="24">
                                <small>{{ itemDetail.varian }}</small>
                            </el-col>
                            <el-col :span="20">
                                <el-input size="mini" placeholder="Notes" v-model="input"></el-input>
                            </el-col>
                        </el-row>

                    </el-col>
                    <el-col :span="1">
                        <el-button type="danger" icon="el-icon-delete" plain/>
                    </el-col>
                    <el-col :span="3">
                        <el-input-number v-model="itemDetail.qty" @change="handleChangeQty" :min="1" :max="100" size="mini"/>
                    </el-col>
                    <el-col :span="2">
                        <b style="color: #409EFF">{{ itemDetail.totalAmount }}</b>
                    </el-col>

                </el-row>

            </el-col>
        </el-row>



        <el-row>
            <el-col :span="12">
                <el-button type="primary" icon="el-icon-goods" plain @click="continueShopping">Continue Shopping</el-button>
            </el-col>

            <el-col :span="12" align="end">
                <el-row>
                    <el-col :span="17">
                        <b>Subtotal Belanja ({{ totalCount }})</b> :  <b style="color: #409EFF">{{ subTotal }}</b>
                    </el-col>
                    <el-col :span="7">
                        <el-button type="success" icon="el-icon-shopping-cart-full" plain>Purchase Requesition</el-button>
                    </el-col>
                </el-row>

            </el-col>
        </el-row>

    </div>
</template>

<script lang="ts" src="./marketplace-cart.component.ts">
</script>

<style lang="scss" scoped>
    .row {
        padding: 0px 10px 10px 10px;

        .set-border {
            border: 1px dashed #d9d9d9;
            padding: 5px;
        }

        .itemDetail {
            border-bottom: 1px dashed #d9d9d9;
            &:last-child {
                border-bottom: 0px;
            }

            .image-thumbnail {
                padding: 5px;
                height: 96px;
                display: block;
                margin-left: auto;
                margin-right: auto;
            }

            .getDetail {
                cursor: pointer;
            }
        }
    }

    .el-row {
        margin-bottom: 15px;
    }
</style>
