<template>
    <div class="bidding-evaluation">
        <div class="card-view">
            <el-row>
                <div>
                    <el-col :span="12">
                        <div class="grid-content bg-purple">
                            <el-form ref="productCatalog" label-position="left" label-width="130px"
                                     size="mini">
                                <el-form-item label="Bidding No">
                                    {{ evaluation.biddingNo }}
                                </el-form-item>
                                <el-form-item label="Bidding Name">
                                    {{ evaluation.biddingName }}
                                </el-form-item>
                                <el-form-item label="Currency">
                                    {{ evaluation.biddingName }}
                                </el-form-item>
                                <el-form-item label="Proposal Price">
                                    {{ evaluation.price }}
                                </el-form-item>
                                <el-form-item label="Bidding Type">
                                    {{ evaluation.biddingTypeName }}
                                </el-form-item>
                                <el-form-item label="Evaluation">
                                    {{ evaluation.evaluation }}
                                </el-form-item>
                                <el-form-item label="Document ">
                                    <el-button class="button"
                                               icon="el-icon-download"
                                               size="mini"
                                               type="primary"
                                    >
                                        {{ evaluation.attachment }}
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
                                    {{ evaluation.eventTypeName }}
                                </el-form-item>
                                <el-form-item label="Vendor Name">
                                    {{ evaluation.vendor }}
                                </el-form-item>
                                <el-form-item label="PIC">
                                    {{ evaluation.pic }}
                                </el-form-item>
                                <el-form-item label="Date Submit">
                                    {{ evaluation.eventTypeName }}
                                </el-form-item>
                                <el-form-item label="Submission Deadline">
                                    {{ evaluation.date }}
                                </el-form-item>
                                <el-form-item label="Pass Or Fail">
                                    <template>
                                        <el-select v-model="value" allow-create placeholder="Select" size="mini"
                                                   style="width: 25%">
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
                    <el-table :data="biddingLineList"
                              size="mini"
                              border
                              >
                        <el-table-column label="No" min-width="30">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Product" min-width="100" prop="productName"/>
                        <el-table-column label="Sub Item" min-width="60" sortable>
                            <template slot-scope="{ row, $index }">
                                <el-button class="button" icon="el-icon-search" size="mini" type="primary"
                                           @click="viewSubItem(row,$index)">
                                    View
                                </el-button>
                            </template>
                        </el-table-column>
                        <el-table-column label="Qty" min-width="50" prop="quantity"/>
                        <el-table-column label="UoM" min-width="50" prop="uomName"/>
                        <el-table-column align="right" label="Ceiling Price/Unit" min-width="80">
                            <template slot-scope="{ row }">
                                {{ row.ceilingPrice | formatCurrency }}
                            </template>

                        </el-table-column>
                        <el-table-column align="right" label="Total Ceiling Price" min-width="80">
                            <template slot-scope="{ row }">
                                {{ row.totalCeilingPrice | formatCurrency }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Delivery Date" min-width="70" prop="deliveryDate">
                        </el-table-column>
                        <el-table-column label="Price submision/unit" min-width="110" prop="">
                            <template slot-scope="{ row, $index }">
                                <el-input-number v-model="row.ceilingPrice1" v-inputmask="{'alias': 'currency'}"
                                                 :step="50000" clearable controls-position="right"
                                                 @change="value => onQuantityOrderedChanged(row, $index, value)">
                                </el-input-number>
                            </template>
                        </el-table-column>
                        <el-table-column label="Total Price Submission" min-width="70" prop="">
                            <template slot-scope="{ row }">
                                {{ row.totalpricesubmision | formatCurrency }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Delivery Date" min-width="70" prop="">
                            <template slot-scope="{ row }">
                                {{ row.delivery | formatDate }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Remark" min-width="50" prop="remark"/>
                        <el-table-column label="Document" min-width="50" prop="remark"/>
                    </el-table>
                </div>
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
