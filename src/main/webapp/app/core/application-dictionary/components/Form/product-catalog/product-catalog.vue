<template>
    <div class="app-container">
        <el-row v-if="index" class="header">
            <el-col :span="24">

                <el-button
                    class="button"
                    size="mini"
                    type="primary"
                    icon="el-icon-search"
                    @click="onClick('filter')"/>

                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary"
                    icon="el-icon-plus"
                    @click="onClick('add')"/>

                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary"
                    icon="el-icon-edit"/>

                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="mini"
                    type="danger"
                    icon="el-icon-delete"
                    @click="onClick('remove')"/>

                <el-button
                    class="button"
                    size="mini"
                    type="primary"
                    icon="el-icon-upload2"
                    @click="onClick('import')">Import</el-button>
                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary"
                    icon="el-icon-download">Export</el-button>

            </el-col>
        </el-row>
<!--
        <el-row class="filter">
            <el-col :span="24">

                <el-form ref="form" label-width="100px" size="mini" v-model="filter" @submit.native.prevent="verificationFilter">
                    <el-row>

                        <el-col :span="10">
                            <el-form-item label="Address ID :" prop="vendor">
                                <el-select
                                    class="form-input"
                                    clearable filterable remote reserve-keyword
                                    v-model="filter.vendor"
                                    :remote-method="retrieveAllVendorRecords"
                                    :loading="remoteProcessing" >
                                    <el-option
                                        v-for="item in vendorOptions"
                                        :key="item.key"
                                        :label="item.value+' - '+item.name"
                                        :value="item.key" />
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="1">
                            <el-form-item label-width="5px">
                                <el-button
                                    size="medium"
                                    @click="verificationFilter"
                                    :loading="remoteProcessing"
                                    icon="el-icon-search"/>
                            </el-form-item>
                        </el-col>

                    </el-row>
                </el-form>

            </el-col>
        </el-row>
-->
        <el-row v-if="index" class="main" ref="tableWrapper">
            <el-col :span="24" >

                <el-tabs
                    v-model="activeName"
                    @tab-click="handleClick">

                    <el-tab-pane
                        v-for="(item, index) in tabTitleOptions"
                        :key="item.value"
                        :label="item.name"
                        :name="item.value">

                        <keep-alive>

                            <catalog-grid
                                ref="catalogGrid"
                                v-if="activeName === item.value"
                                :status="''+index"
                                @selectedRows="selectedRows"/>

                        </keep-alive>
                    </el-tab-pane>
                </el-tabs>

            </el-col>
        </el-row>

        <el-row v-if="!index" class="main" ref="tableWrapper">
            <el-col :span="24" class="tab-container">

                <product-information
                    @closeProductInformation="closeProductInformation" />

            </el-col>
        </el-row>

            <!-- =========================================================================== -->

        <el-dialog
            width="40%"
            :visible.sync="dialogConfirmationVisible"
            :title="dialogTitle">

            <template>

                <el-row v-if="dialogType=='export'" :gutter="16">
                    <el-col :span="24" :offset="0">
                        export
                    </el-col>
                </el-row>

                <el-row v-else-if="dialogType=='import'" :gutter="16">
                    <el-col :span="24" :offset="0">

                        <!-- ================================== -->
                        <template>
                            <el-form label-width="110px" size="mini">

                                <!--<el-form-item label="Files of Type:">
                                    <el-select
                                        size="mini"
                                        v-model="bookType"
                                        @change="changeBookType"
                                        placeholder="Select BookType">
                                        <el-option
                                            v-for="typeBook in chooseBookTypeImport"
                                            :key="typeBook.type"
                                            :label="typeBook.name"
                                            :value="typeBook.type"/>
                                    </el-select>
                                </el-form-item>-->

                                <el-form-item
                                    label="Select file :"
                                    prop="importProductCatalog">

                                    <el-upload
                                        ref="import"
                                        action="/api/import-product-catalog"
                                        v-model="importProductCatalog"
                                        :accept="accept"
                                        :on-success="onUploadSuccess"
                                        :on-error="handleImportError"
                                        :headers="importHeaders"
                                        :auto-upload="false"
                                        :before-upload="handleBeforeUpload"
                                        :limit="1"
                                        :on-exceed="handleExceed">
                                        <el-button
                                            style="margin-left: 0px;"
                                            size="mini"
                                            type="primary"
                                            icon="el-icon-search"
                                            slot="trigger">Select File</el-button>
                                        <el-button
                                            style="margin-left: 0px;"
                                            size="mini"
                                            type="success"
                                            icon="el-icon-upload2"
                                            @click="submitImport">Import</el-button>

                                        <div class="el-upload__tip" slot="tip">csv files with a size less than ...kb</div>
                                    </el-upload>

                                </el-form-item>

                            </el-form>

                        </template>

                        <!-- ====================================== -->

                    </el-col>
                </el-row>

                <el-row v-else :gutter="16">
                    <el-col :span="24" :offset="0">
                        {{ dialogMessage }}
                    </el-col>
                </el-row>

                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        v-if="dialogType=='remove'"
                        @click="dialogButtonAction(dialogType)"
                        :icon="dialogButtonIcon"
                        :type="dialogButtonType">
                            {{ dialogButton }}
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="dialogConfirmationVisible = false">
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./product-catalog.component.ts">
</script>

<style lang="scss">
    .el-tabs__header{
        margin: 0px;
    }

    .el-table__fixed{
        box-shadow: none;
    }

    .main {
        padding: 0px;
    }



    .form-input {
        width: 100%;
    }

</style>
