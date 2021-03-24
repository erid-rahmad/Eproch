<template>
    <div class="app-container">
        <div v-if="index">
            <el-row class="header">
                <el-col :span="24">
                    <el-button class="button" style="margin-left: 0px;" size="mini" type="primary" icon="el-icon-plus" @click="onClick('add')" />
                    <el-button class="button" style="margin-left: 0px;" size="mini" type="danger" icon="el-icon-delete" @click="onClick('remove')" />
                    <el-button class="button" size="mini" type="primary" icon="el-icon-download" @click="onClick('export')">Export</el-button>
                </el-col>
            </el-row>
            <el-divider content-position="left">
                <h4>Bidding Submission From Vendor</h4>
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
                            <h7>Tender </h7>
                        </el-form-item>
                        <el-form-item label="Vendor Name" prop="title" required>
                            <el-dropdown>
                                <span class="el-dropdown-link">
                                    Vendor List<i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>PT baru</el-dropdown-item>
                                    <el-dropdown-item>PT lama</el-dropdown-item>
                                    <el-dropdown-item>PT baru lama</el-dropdown-item>
                                    <el-dropdown-item disabled>Action 4</el-dropdown-item>
                                    <el-dropdown-item divided>Action 5</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </el-form-item>
                        <el-form-item label="Ceilling Price" prop="biddingNo" required>
                            <h7>17000</h7>
                        </el-form-item>
                        <el-form-item label="Proposal Price" prop="vurrency" required>
                            <h7>17000</h7>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <el-divider content-position="left">
                <h4>Bidding Submission From Vendor Detail</h4>
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
            <el-form ref="biddingInformation" label-position="left" label-width="150px" size="mini" :model="biddingInformation" :rules="rules">
                <el-row :gutter="24">

                    <el-col :span="12">
                        <el-form-item width="200" label="Grand Total Requarement " prop="Grand Total Requarement">
                            <h7>17000</h7>
                        </el-form-item>
                        <el-form-item width="120" label="Grand Total Submision" prop="biddingNo">
                            <h7>17000</h7>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <div v-else>
            <step-form :biddingrow="rowsa" @back="close" />
        </div>
    </div>
</template>
<script lang="ts" src="./bidding.component.ts">
</script>
<style lang="scss">
    .el-table__fixed,
    .el-table__fixed-right {
        box-shadow: none;
    }

    .main {
        padding: 0px;

        .button {
            width: 100%;
        }
    }

    .header {
        .button {
            margin-bottom: 5px;
        }
    }

    .form-input {
        width: 100%;
    }

</style>
