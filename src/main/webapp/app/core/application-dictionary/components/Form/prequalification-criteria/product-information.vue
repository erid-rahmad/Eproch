<template>
    <div>
        <el-row :gutter="columnSpacing">
            <el-col :span="24">
                <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">
                    Back
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 0px" v-loading.fullscreen.lock="fullscreenLoading" @click="validate">
                    Submit <em class="el-icon-arrow-right"></em>
                </el-button>
            </el-col>
        </el-row>
        <el-form ref="productCatalog" label-position="left" label-width="130px" size="mini" :model="productCatalog" :rules="rules">
            <el-divider content-position="left">
                <h4>Product Information</h4>
            </el-divider>
              <el-form ref="biddingInformation" label-position="left" label-width="150px" size="mini" :model="biddingInformation" :rules="rules">
                <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="Bidding No" prop="" required>
                            <template>
                                <el-col :span="6">
                                    <el-input v-model="asd" class="form-input" clearable disabled></el-input>
                                </el-col>
                                <el-col :span="6">
                                    <el-button type="primary" icon="el-icon-search">Search</el-button>
                                </el-col>
                            </template>
                        </el-form-item>
                        <el-form-item label="Bidding Type" prop="biddingNo" required>
                            <h7>Tender good</h7>
                        </el-form-item>
                        <el-form-item label="Bidding Tytle" prop="biddingNo" required>
                            <h7>Penggadaan kendaraan operasional </h7>
                        </el-form-item>
                        <el-form-item label="Vendor Name" prop="title" required>
                            <el-dropdown>
                                <span class="el-dropdown-link">
                                    PT Sistech Kharisma<i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>PT Sistech Kharisma</el-dropdown-item>
                                    <el-dropdown-item>PT Ingram Micro Indonesia</el-dropdown-item>
                                    <el-dropdown-item>PT Makmur jaya </el-dropdown-item>
                                 
                                </el-dropdown-menu>
                            </el-dropdown>
                        </el-form-item>
                        <el-form-item label="Ceilling Price" prop="biddingNo" required>
                            <h7>29.000.000.000</h7>
                        </el-form-item>
                        <el-form-item label="Proposal Price" prop="vurrency" required>
                            <h7>269.000.000.000</h7>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        
            <el-divider content-position="left">
                <h4>Detail</h4>
            </el-divider>
              <template>
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="date" label="No" width="40">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>
                    <el-table-column label="Requarement">
                        <el-table-column prop="1" label="Product" width="120">
                        </el-table-column>
                        <el-table-column prop="2" label="Brand" width="100">
                        </el-table-column>
                        <el-table-column prop="3" label="Short Descriptopn" width="300">
                        </el-table-column>
                        <el-table-column prop="4" label="Qty" width="100">
                        </el-table-column>
                        <el-table-column prop="5" label="Uom" width="100">
                        </el-table-column>
                        <el-table-column prop="6" label="Ceiiling proce / unit" width="100">
                        </el-table-column>
                        <el-table-column prop="7" label="Total celing price " width="100">
                        </el-table-column>
                        <el-table-column prop="8" label="dalivery date" width="100">
                        </el-table-column>
                    </el-table-column>
                    <el-table-column label="Submission">
                        <el-table-column prop="9" label="Price Per Unit" width="100">
                        </el-table-column>
                        <el-table-column prop="10" label="Total Price" width="100">
                        </el-table-column>
                        <el-table-column prop="11" label="Delivary Date" width="150">
                        </el-table-column>
                    </el-table-column>
                </el-table>
            </template>
            <el-divider content-position="left">
                <h4>Total</h4>
            </el-divider>
            <el-col :span="24">
                <el-col :span="6">
                     <h6>Grand Total Requarement</h6>
                </el-col>
                <el-col :span="6">
                      <h6>29.570.000.000</h6>
                </el-col>

            </el-col>
           
          
         

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
