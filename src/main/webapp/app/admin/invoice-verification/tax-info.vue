<template>
    <div class="app-container">
        <el-form
            ref="form"
            label-width="170px"
            size="mini">

            <el-row style="margin-top: 0px" class="filter">

                <el-col :span="12">
                    <el-form-item label="Tax Period" prop="taxPeriod">
                        <el-input class="form-input" v-model="taxInfo.taxPeriod" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="Tax Currency" prop="taxCurrency">
                        <el-input class="form-input" v-model="taxInfo.taxCurrency" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="Transaction Code" prop="transactionCode">
                        <el-input class="form-input" v-model="taxInfo.transactionCode" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="Status Code" prop="statucCode">
                        <el-input class="form-input" v-model="taxInfo.statucCode" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="Doc Code" prop="docCode">
                        <el-input class="form-input" v-model="taxInfo.docCode" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="Year / Number" prop="year">
                        <el-input class="form-input" v-model="taxInfo.year" @change="setTaxInfo" clearable />
                    </el-form-item>

                </el-col>

                <el-col :span="12">
                    <el-form-item label="Return Doc Type" prop="returnDocType">
                        <el-input class="form-input" v-model="taxInfo.returnDocType" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="Replacement Serial No." prop="replacementSerialNo">
                        <el-input class="form-input" v-model="taxInfo.replacementSerialNo" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="Tax Rate Area" prop="taxRateArea">
                        <el-input class="form-input" v-model="taxInfo.taxRateArea" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="Tax Expl. Code" prop="taxExplCode">
                        <el-input class="form-input" v-model="taxInfo.taxExplCode" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="Business Unit" prop="businessUnit">
                        <el-input class="form-input" v-model="taxInfo.businessUnit" @change="setTaxInfo" clearable />
                    </el-form-item>
                    <el-form-item label="SSP Date" prop="sspDate">
                        <el-input class="form-input" v-model="taxInfo.sspDate" @change="setTaxInfo" clearable />
                    </el-form-item>

                </el-col>

            </el-row>
        </el-form>

    </div>
</template>

<script lang="ts" src="./tax-info.component.ts">
</script>

<style lang="scss">
    .el-dialog__body{
        padding: 10px 20px;
    }
</style>
