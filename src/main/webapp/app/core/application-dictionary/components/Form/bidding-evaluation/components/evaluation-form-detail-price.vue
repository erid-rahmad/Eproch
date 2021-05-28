<template>
    <div class="container">
        <div>
            <el-col :span="12">
                <div>
                    <el-form ref="productCatalog" label-position="left" label-width="130px"
                             size="mini">
                        <el-form-item label="Bidding No">
                            {{ biddingSubmission.biddingNo }}
                        </el-form-item>
                        <el-form-item label="Bidding Name">
                            {{ biddingSubmission.biddingName }}
                        </el-form-item>
                        <el-form-item label="Currency">
                            {{ biddingSubmission.currencyName }}
                        </el-form-item>
                        <el-form-item label="Proposal Price">
                            {{ biddingSubmission.ceilingPrice }}
                        </el-form-item>
                        <el-form-item label="Event Type">
                            {{ biddingSubmission.eventTypeName }}
                        </el-form-item>
                        <el-form-item label="Bidding Type">
                            {{ biddingSubmission.biddingTypeName }}
                        </el-form-item>
                        <el-form-item label="Document " style="width: 50%">
                            <el-button class="button"
                                       icon="el-icon-download"
                                       size="mini"
                                       type="primary"
                            >
                                {{ proposalPrice.attachment }}
                            </el-button>

                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
            <el-col :span="12">
                <div class="grid-content bg-purple-light">
                    <el-form ref="productCatalog" label-position="left" label-width="300px"
                             size="mini">
                        <el-form-item label="Event Type">
                            {{ biddingSubmission.eventTypeName }}
                        </el-form-item>
                        <el-form-item label="Vendor Name">
                            {{ biddingSubmission.vendorName }}
                        </el-form-item>
                        <el-form-item label="PIC">
                            {{ biddingSubmission.pic }}
                        </el-form-item>
                        <el-form-item label="Date Submit">
                            {{ biddingSubmission.date }}
                        </el-form-item>
                        <el-form-item label="Submission Deadline">
                            {{ biddingSubmission.date }}
                        </el-form-item>
                        <el-form-item label="Score">
                            <el-input v-model="evaluationResultLine.score" placeholder="Please input"></el-input>
                        </el-form-item>
                        <el-form-item label="Evaluation">
                            <template>
                                <el-select v-model="evaluationResultLine.status" placeholder="Select" size="mini">
                                    <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                        size="mini">
                                    </el-option>
                                </el-select>
                            </template>
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
        </div>
        <div>
            <el-table :data="proposalPriceLine"
                      border
                      size="mini"
                      style="width: 100%"
                      >
                <el-table-column label="No" min-width="30">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="Product" min-width="100" prop="biddingLineName"/>
                <el-table-column label="Sub Item" min-width="60" sortable>
                    <template slot-scope="{ row, $index }">
                        <el-button class="button" icon="el-icon-search" size="mini" type="primary"
                                   @click="viewSubItem(row,$index)">
                            View
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="Qty" min-width="50" prop="biddingLineQuantity"/>
                <el-table-column label="UoM" min-width="50" prop="uomName"/>
                <el-table-column align="right" label="Ceiling Price/Unit" min-width="80">
                    <template slot-scope="{ row }">
                        {{ row.biddingLineCeilingPrice | formatCurrency }}
                    </template>

                </el-table-column>
                <el-table-column align="right" label="Total Ceiling Price" min-width="80">
                    <template slot-scope="{ row }">
                        {{ row.biddingLineTotalCeilingPrice |formatCurrency }}
                    </template>
                </el-table-column>
                <el-table-column label="Delivery Date" min-width="70" prop="deliveryDate">
                    <template slot-scope="{ row }">
                        {{ row.biddingLineDeliveryDate }}
                    </template>
                </el-table-column>
                <el-table-column label="Price submision/unit" min-width="110" prop="proposedPrice">

                </el-table-column>
                <el-table-column label="Total Price Submission" min-width="70" prop="">
                    <template slot-scope="{ row }">
                        {{ row.totalPriceSubmission }}
                    </template>
                </el-table-column>
                <el-table-column label="Delivery Date" min-width="70" prop="">
                    <template slot-scope="{ row }">
                        {{ row.deliveryDate }}
                    </template>
                </el-table-column>
                <el-table-column label="Remark" min-width="50" prop="biddingLineRemark"/>
                <el-table-column label="Document" min-width="50" prop="doc"/>
            </el-table>
        </div>
        <div style="margin-bottom: 20px;margin-top: 20px">
            <el-row>
                <el-col :span="6">
                    <div class="grid-content bg-purple">-</div>
                </el-col>
                <el-col :span="6">
                    <div class="grid-content bg-purple-light">
                        <el-button size="mini"
                                   type="primary" @click="save()">
                            Save
                        </el-button>
                    </div>
                </el-col>
            </el-row>
        </div>


    </div>
</template>

<script lang="ts" src="./evaluation-form-detail-price.component.ts"></script>

<style lang="scss">
.bidding-evaluation {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 36px auto;

    td.document-submission .cell {
        padding: 0;

        .el-row .el-col.border {
            border-bottom: none !important;
            border-left: none !important;
            padding: 4px 10px;

            &:last-child {
                border-right: none !important;
            }
        }

        .el-row:first-child .el-col.border {
            border-top: none !important;
        }
    }

    .el-divider--horizontal {
        margin-top: 32px;
        margin-bottom: 16px;
    }

    .form-wrapper {
        .el-scrollbar__wrap {
            overflow-x: hidden;
            padding: 15px;
        }
    }


    .toolbar {
        padding: 4px;
    }

    .vendor-scoring tbody td {
        height: 35px;
    }
}

.el-tabs__header {
    margin: 0px;
}

.el-table__fixed {
    box-shadow: none;
}

.main {
    padding: 0px;
}

.form-input {
    width: 100%;
}

</style>
