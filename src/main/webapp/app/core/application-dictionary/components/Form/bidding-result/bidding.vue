<template>
    <div class="app-container">
        <div v-if="index">
            <el-row class="header">
                <!-- <el-col :span="24">
                    <el-button class="button" style="margin-left: 0px;" size="mini" type="primary" icon="el-icon-plus" @click="onClick('add')" />
                    <el-button class="button" style="margin-left: 0px;" size="mini" type="danger" icon="el-icon-delete" @click="onClick('remove')" />
                    <el-button class="button" size="mini" type="primary" icon="el-icon-download" @click="onClick('export')">Export</el-button>
                </el-col> -->
            </el-row>
            <el-form ref="biddingInformation" label-position="left" label-width="150px" size="mini" :model="biddingInformation" :rules="rules">
                <el-row :gutter="24">
                    <el-col :span="6">
                        <el-form-item label="Vendor Name" required>
                            <el-select
                                clearable
                                filterable
                                placeholder="Select Vendors"
                                style="width: 100%"
                            >
                                <el-option label="Vendor A" value="vendorA"></el-option>
                                <el-option label="Vendor B" value="vendorB"></el-option>
                                <el-option label="Vendor C" value="vendorC"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="Ceilling Price" required>
                            <el-input
                                v-inputmask="{'alias': 'currency'}"
                                size="mini"
                            >
                                <template slot="prepend">IDR</template>
                            </el-input>
                        </el-form-item>
                        <el-form-item label="Proposal Price" required>
                            <el-input
                                v-inputmask="{'alias': 'currency'}"
                                size="mini"
                            >
                                <template slot="prepend">IDR</template>
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <el-table
                border
                :data="tableData"
                show-summary
                size="mini"
                stripe
                :summary-method="calculateSummary"
                style="width: 100%"
            >
                <el-table-column prop="date" label="No" width="50">
                    <template slot-scope="row">
                        {{ row.$index+1 }}
                    </template>
                </el-table-column>
                <el-table-column label="Requirement">
                    <el-table-column prop="product" label="Product" width="150">
                    </el-table-column>
                    <el-table-column prop="brand" label="Brand" width="100">
                    </el-table-column>
                    <el-table-column prop="description" label="Short Descriptopn" width="150">
                    </el-table-column>
                    <el-table-column prop="quantity" label="Qty" width="100">
                    </el-table-column>
                    <el-table-column prop="uom" label="UoM" width="100">
                    </el-table-column>
                    <el-table-column align="right" prop="ceilingPrice" label="Ceiiling Price/Unit" width="200">
                        <template slot-scope="{ row }">
                            {{ row.ceilingPrice | formatCurrency }}
                        </template>
                    </el-table-column>
                    <el-table-column align="right" prop="totalCeilingPrice" label="Total celing price " width="200">
                        <template slot-scope="{ row }">
                            {{ row.totalCeilingPrice | formatCurrency }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="deliveryDate" label="Delivery date" width="100">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="Submission">
                    <el-table-column align="right" prop="unitPrice" label="Price Per Unit" width="200">
                        <template slot-scope="{ row }">
                            {{ row.unitPrice | formatCurrency }}
                        </template>
                    </el-table-column>
                    <el-table-column align="right" prop="totalPrice" label="Total Price" width="200">
                        <template slot-scope="{ row }">
                            {{ row.totalPrice | formatCurrency }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="submissionDeliveryDate" label="Delivery Date" width="150">
                    </el-table-column>
                </el-table-column>
            </el-table>
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
