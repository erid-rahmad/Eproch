<template>
    <div class="app-container">
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.country.home.title')" id="country-heading">Countries</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <div class="table-container">
            <div class="toolbar float-top-right">
                
                <el-button 
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary" 
                    icon="el-icon-search"
                    :loading="isSaving"
                    @click="handleModalVisible('filterRecord')"
                    :title="$t('opusWebApp.country.home.filterRecord')"/>

                <el-button 
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary" 
                    icon="el-icon-plus"
                    @click="add()" 
                    :title="$t('opusWebApp.country.home.createLabel')">
                    Add
                </el-button>
                <el-button
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary"
                    icon="el-icon-refresh"
                    :title="$t('opusWebApp.country.home.refresh')"
                    :loading="isSaving"
                    @click="clear()"
                />
                <el-button 
                    :loading="downloadLoading"
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary" 
                    icon="el-icon-download" 
                    @click="handleModalVisible('downloadDoc')"
                    :title="$t('opusWebApp.country.home.downloadDocument')"/>
                    <!--@click="handleDownload"-->

                <el-button 
                    :loading="downloadLoading"
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary" 
                    icon="el-icon-upload2" 
                    disabled
                    :title="$t('opusWebApp.country.home.upload')"/>
                    
                <el-button 
                    :loading="downloadLoading"
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary" 
                    icon="el-icon-printer" 
                    disabled
                    :title="$t('opusWebApp.country.home.print')"/>
                
                <el-button 
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary" 
                    icon="el-icon-view" 
                    @click="handleModalVisible('visibleColumn')"
                    :title="$t('opusWebApp.country.home.visibleColumn')"/>
                
                <el-divider direction="vertical"></el-divider>
                
                <el-button
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary"
                    icon="el-icon-document-copy"
                    :title="$t('opusWebApp.country.home.duplicate')"
                    plain
                    @click="handleSelection('duplicate')"
                />
                <el-button
                    style="margin-left: 0px;"
                    @click="handleSelection('remove')"
                    type="danger"
                    size="mini"
                    icon="el-icon-delete"
                    plain
                    :title="$t('entity.action.delete')"/>
            </div>
            <el-table
                v-loading="listLoading"
                fit
                max-height="560px"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                :data="countries"
                :default-sort="{prop: 'name', order: 'ascending'}"
                :empty-text="$t('opusWebApp.country.home.notFound')"
                @sort-change="changeOrder"
                @select="handleSelectionChange"
                @select-all="handleSelectionChangeAll"
            >
            <!--
                :key="tableKey"
                ref="multipleTable"
                highlight-current-row
                @current-change="changeClassificationSelection"
            -->
                <el-table-column
                    type="selection"
                    align="center"/>

                <el-table-column 
                    type="expand"
                    align="center">
                    <template slot-scope="props">
                        <p>{{$t('opusWebApp.country.name')}}: {{ props.row.name }}</p>
                        <p>{{$t('opusWebApp.country.code')}}: {{ props.row.code }}</p>
                        <p>{{$t('opusWebApp.country.currency')}}: {{ props.row.currencyName }}</p>
                    </template>
                </el-table-column>

                <el-table-column
                    v-if="showName"
                    prop="name"
                    sortable
                    :label="$t('opusWebApp.country.name')"
                >

                    <template slot-scope="scope">
                        <template v-if="scope.row.edit">
                            <el-input
                                v-model="scope.row.name"
                                class="edit-input"
                                size="mini"
                                />
                        </template>
                        <span v-else>{{ scope.row.name }}</span>
                    </template>

                </el-table-column>
                
                <el-table-column
                    v-if="showCode"
                    prop="code"
                    align="center"
                    sortable
                    :label="$t('opusWebApp.country.code')"
                >
                    <template slot-scope="scope">
                        <template v-if="scope.row.edit">
                            <el-input
                                v-model="scope.row.code"
                                class="edit-input"
                                size="mini"
                                />
                        </template>
                        <span v-else>{{ scope.row.code }}</span>
                    </template>

                </el-table-column>

                <el-table-column
                    v-if="showCurrency"
                    prop="currencyCode"
                    align="center"
                    sortable
                    :label="$t('opusWebApp.country.currency')"
                >
                    <template slot-scope="scope">
                        <template v-if="scope.row.edit">
                            <el-select
                                filterable
                                size="mini"
                                v-model="scope.row.currencyId"
                                placeholder="Select Parent Currency"
                            >
                                <el-option
                                    v-for="currencyOption in currencies"
                                    :key="currencyOption.id"
                                    :label="currencyOption.name"
                                    :value="currencyOption.id"
                                />
                            </el-select>
                        </template>
                        <span v-else>{{ scope.row.currencyName }}</span>
                    </template>

                </el-table-column>

                <el-table-column
                    v-if="showActive"
                    align="center"
                    sortable
                    :label="$t('opusWebApp.country.active')"
                >
                    <template slot-scope="scope">
                        <el-checkbox
                            v-bind:checked="checkedActive"
                            @change="checkActive(scope.row)"
                            align="center"
                            />
                    </template>
                </el-table-column>
                <el-table-column
                    label="#"
                    align="center"
                    width="120px"
                >
                    <template slot-scope="scope">

                        <span v-if="scope.row.duplicate">
                            <el-button
                                style="margin-left: 0px;"
                                class="cancel-btn"
                                size="mini"
                                icon="el-icon-close"
                                type="danger"
                                plain
                                @click="cancelDuplicate(scope.row, scope.$index)"
                                :title="$t('entity.action.cancel')"
                            />

                            <el-button
                                style="margin-left: 0px;"
                                type="success"
                                size="mini"
                                icon="el-icon-edit-outline"
                                plain
                                @click="confirmDuplicate(scope.row)"
                                :title="$t('entity.action.save')"
                            />
                        </span>

                        <span v-else>
                            <el-button
                                style="margin-left: 0px;"
                                v-if="scope.row.edit"
                                class="cancel-btn"
                                size="mini"
                                icon="el-icon-close"
                                type="warning"
                                plain
                                @click="cancelEdit(scope.row)"
                                :title="$t('entity.action.cancel')"
                            />

                            <el-button
                                style="margin-left: 0px;"
                                v-if="scope.row.edit"
                                type="success"
                                size="mini"
                                icon="el-icon-edit-outline"
                                plain
                                @click="confirmEdit(scope.row)"
                                :title="$t('entity.action.save')"
                            />
                            <el-button
                                style="margin-left: 0px;"
                                v-else
                                type="primary"
                                size="mini"
                                icon="el-icon-edit"
                                @click="scope.row.edit=!scope.row.edit"
                                plain
                                :title="$t('entity.action.edit')"
                            />
                        </span>
                        
                    </template>

                </el-table-column>
                
            </el-table>
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="loadPage"
                :current-page.sync="page"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="itemsPerPage"
                layout="total, sizes, prev, pager, next, jumper"
                :total="queryCount">
            </el-pagination>
        </div>

        <el-dialog
            :visible.sync="showDeleteDialog"
            width="30%"
            :title="$t('entity.delete.title')"
        >
            <template>
                <span>{{ $t('opusWebApp.country.delete.question', {'id': ""}) }}</span>
                <div slot="footer">
                    <el-button 
                        size="mini"
                        icon="el-icon-delete" 
                        type="danger" 
                        @click="removeCountry()">
                        {{ $t('entity.action.delete') }}
                    </el-button>
                    <el-button 
                        size="mini"
                        icon="el-icon-close" 
                        @click="closeDialog()">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>

        </el-dialog>

        <el-dialog
            :visible.sync="showFilterRecord"
            width="50%"
            :title="$t('opusWebApp.country.home.filterRecord')"
            >
            <template>
                <el-tabs v-model="activeTabsSearch.active" @tab-click="handleClickTabSearch">
                    <el-tab-pane label="Lookup Record" name="first">
                        
                        <el-form label-width="100px" size="mini">
                            <el-form-item label="Name">
                                <el-input 
                                    v-model="listQuery.name"
                                    placeholder="Name" 
                                    style="width: 200px;"
                                    size="mini"
                                    @keyup.enter.native="handleBasicFilter" />
                            </el-form-item>
                            <el-form-item label="Code">
                                <el-input 
                                    v-model="listQuery.code"
                                    placeholder="Code" 
                                    style="width: 200px;"
                                    size="mini"
                                    @keyup.enter.native="handleBasicFilter" />
                            </el-form-item>
                            <el-form-item label="Currency">
                                <el-select
                                    size="mini"
                                    filterable
                                    v-model="listQuery.currencyId"
                                    placeholder="Select Parent Currency"
                                >
                                    <el-option
                                        v-for="currencyOption in currencies"
                                        :key="currencyOption.id"
                                        :label="currencyOption.name"
                                        :value="currencyOption.id"
                                    />
                                </el-select>
                            </el-form-item>
                            <el-form-item>
                                <el-button 
                                    style="margin-left: 0px;"
                                    size="mini"
                                    type="primary" 
                                    icon="el-icon-search"
                                    :loading="isSaving"
                                    @click="handleBasicFilter"
                                    :title="$t('opusWebApp.country.home.search')">
                                    Search
                                </el-button>

                                <el-button 
                                    style="margin-left: 0px;"
                                    size="mini"
                                    type="default" 
                                    icon="el-icon-close"
                                    :loading="isSaving"
                                    @click="clear"
                                    :title="$t('opusWebApp.country.home.clear')">
                                    Clear
                                </el-button>
                            </el-form-item>
                        </el-form>

                    </el-tab-pane>

                    <el-tab-pane label="Advanced" name="second">

                        <el-button
                            style="margin-left: 0px;"
                            type="primary"
                            size="mini"
                            icon="el-icon-plus"
                            plain
                            @click="addFilterAdvance"
                        >Add</el-button>

                        <el-button
                            style="margin-left: 0px;"
                            type="primary"
                            size="mini"
                            icon="el-icon-search"
                            :loading="isSaving"
                            @click="executeFilterAdvance"
                        >Execute</el-button>

                        <el-button
                            style="margin-left: 0px;"
                            type="default"
                            size="mini"
                            icon="el-icon-close"
                            plain
                            :loading="isSaving"
                            @click="clear"
                        >Clear</el-button>

                        <el-table
                            :data="itemListQueryAdvance"
                            @selection-change="handleSelectionChangeFilterAdvance"
                            fit
                            style="width: 100%"
                            :empty-text="$t('opusWebApp.country.home.notFound')"
                        >
                        <!--
                            ref="multipleTableFilterAdvance"
                        -->
                            <el-table-column
                                type="selection"
                                align="center"/>
                            
                            <el-table-column
                                prop="column"
                                align="center"
                                label="Column"
                            >
                                <template slot-scope="scope">
                                    <el-select
                                        filterable
                                        size="mini"
                                        v-model="scope.row.column"
                                        placeholder="Select Column"
                                        @change="columnOptionType"
                                    >
                                        <el-option
                                            v-for="columnOption in column"
                                            :key="columnOption.code"
                                            :label="columnOption.value"
                                            :value="columnOption"
                                        >
                                            <span style="float: left">{{ columnOption.value }}</span>
                                            <span style="float: right; color: #8492a6; font-size: 13px">{{ columnOption.type }}</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>

                            <el-table-column
                                prop="query"
                                align="center"
                                label="Query"
                            >
                                <template slot-scope="scope">
                                    <el-select
                                        filterable
                                        size="mini"
                                        v-model="scope.row.query"
                                        placeholder="Select Query"
                                    >
                                        <el-option
                                            v-for="queryOption in operators"
                                            :key="queryOption.code"
                                            :label="queryOption.value"
                                            :value="queryOption"
                                        />
                                    </el-select>
                                </template>
                            </el-table-column>

                            <el-table-column
                                prop="queryValue"
                                align="center"
                                label="Query Value"
                            >
                                <template slot-scope="scope">
                                    <el-input
                                        class="edit-input"
                                        size="mini"
                                        v-model="scope.row.queryValue"
                                    >
                                    </el-input>
                                </template>
                            </el-table-column>

                            <el-table-column
                                align="center"
                                label="#"
                            >
                                <template slot-scope="scope">
                                    <el-button
                                        style="margin-left: 0px;"
                                        type="danger"
                                        size="mini"
                                        icon="el-icon-close"
                                        plain
                                        @click="removeFilterAdvance(scope.row, scope.$index)"
                                    />
                                </template>

                            </el-table-column>

                        </el-table>
                        
                    </el-tab-pane>
                </el-tabs>
            </template>
            
        </el-dialog>

        <el-dialog
            width="30%"
            :visible.sync="showVisibleColumn"
            :title="$t('opusWebApp.country.home.visibleColumn')"
        >
            <template>
                <div class="container">
                    <el-checkbox
                        v-model="showName"
                        label="Name"
                        />
                    <el-checkbox
                        v-model="showCode"
                        label="Code"
                        />
                    <el-checkbox
                        v-model="showCurrency"
                        label="Currency"
                        />
                    <el-checkbox
                        v-model="showActive"
                        label="Active"
                        />
                </div>
                <div slot="footer">
                    <el-button 
                        size="mini"
                        icon="el-icon-close" 
                        @click="closeDialog()">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>

        </el-dialog>

        <el-dialog
            width="30%"
            :visible.sync="showDownloadDoc"
            :title="$t('opusWebApp.country.home.downloadDocument')"
        >
            <template>
                <el-form label-width="100px" size="mini">
                    
                    <el-form-item label="Name">
                        <el-input 
                            v-model="listTypeBook.name"
                            placeholder="Name" 
                            style="width: 200px;"
                            size="mini" />
                    </el-form-item>

                    <el-form-item label="Files of Type:">
                        <el-select
                            size="mini"
                            v-model="listTypeBook.type"
                            placeholder="Select Type Excel"
                        >
                            <el-option
                                v-for="typeBook in chooseBookType"
                                :key="typeBook.type"
                                :label="typeBook.type"
                                :value="typeBook.type"
                            />
                        </el-select>
                    </el-form-item>

                    <el-form-item >
                        <el-checkbox
                            v-model="checkSelected"
                            @change="handleDownloadSelection"
                            align="center"
                            label="Export current row only?"
                            >
                            
                        </el-checkbox>
                    </el-form-item>
                    
                </el-form>
                <div slot="footer">
                    <el-button 
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary" 
                        icon="el-icon-download"
                        v-bind:disabled="buttonDisable"
                        :loading="listLoading"
                        @click="handleDownload"
                        :title="$t('opusWebApp.country.home.search')">
                        Download
                    </el-button>
                    <el-button 
                        icon="el-icon-close" 
                        size="mini"
                        type="default" 
                        @click="closeDialog()">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
            
        </el-dialog>

    </div>
</template>

<style>
  .el-table .primary-row {
    background: #F5F7FA;
  }

  .el-table .warning-row {
    background: oldlace;
  }
</style>

<script lang="ts" src="./country.component.ts">
</script>
