<template>
  <div
    v-loading="generating"
    class="app-container"
  >
    <el-row>
      <el-col
        :span="24"
        style="padding: 4px"
      >
        <el-button
          class="button"
          icon="el-icon-plus"
          size="mini"
          type="primary"
        >
          Add
        </el-button>
        <el-button
          class="button"
          size="mini"
          type="primary"
          v-if="selectionTypeCheck"
          @click="showQuoteDialog"
        >
          Quote Supplier
        </el-button>
      </el-col>
    </el-row>
    Request for Quotation
    <div>
      <el-table
        v-loading="loading"
        ref="mainTable"
        border
        :data="gridData"
        :default-sort="gridSchema.defaultSort"
        :empty-text="gridSchema.emptyText"
        :height="gridSchema.height"
        highlight-current-row
        stripe
        size="mini"
        style="width: 100%"
        @sort-change="changeOrder"
        @current-change="onSelectionChanged"
      >
        <el-table-column
          label="Quotation No"
          min-width="100"
          prop="documentNo"
          sortable
        ></el-table-column>
        <el-table-column
          label="Organization"
          min-width="100"
          prop="adOrganizationName"
          sortable
        ></el-table-column>
        <el-table-column
          label="Classification"
          min-width="100"
          prop="businessClassificationName"
          sortable
        ></el-table-column>
        <el-table-column
          label="Category"
          min-width="100"
          prop="businessCategoryName"
          sortable
        ></el-table-column>
        <el-table-column
          label="Cost Center"
          min-width="100"
          prop="costCenterName"
          sortable
        ></el-table-column>
        <el-table-column
          label="Date Trx"
          min-width="100"
          prop="dateTrx"
          sortable
        ></el-table-column>
        <el-table-column
          label="Selection Method"
          min-width="100"
          sortable
        >
          <template slot-scope="{ row }">
            {{ formatSelection(row.selectionMethod) }}
          </template>
        </el-table-column>
        <el-table-column
          label="Warehouse"
          min-width="100"
          prop="warehouseName"
          sortable
        ></el-table-column>
        <el-table-column
          label="Currency"
          min-width="100"
          prop="currencyName"
          sortable
        ></el-table-column>
        <el-table-column
          label="Date Promised"
          min-width="100"
          prop="datePromised"
          sortable
        ></el-table-column>
        <el-table-column
          label="Date Required"
          min-width="100"
          prop="dateRequired"
          sortable
        ></el-table-column>
        <el-table-column
          label="Total Amount"
          min-width="100"
          prop="grandTotal"
          sortable
        ></el-table-column>
      </el-table>
      <el-pagination
        ref="pagination"
        background
        :current-page.sync="page"
        layout="sizes, prev, pager, next"
        mini
        :page-sizes="[10, 20, 50, 100]"
        :page-size="itemsPerPage"
        :total="queryCount"
        @size-change="changePageSize"
      />
    </div>
    <el-dialog
      width="70%"
      :visible.sync="quoteDialog"
      title="Quote Supplier"
      >
      <el-form label-position="left" label-width="150px" :rules="quoteFormValidationSchema" :model="quoteForm" ref="reqForm">
        <el-row>
          <el-col span="12">
            <el-form-item label="Date Required" prop="dateRequired">
              <el-date-picker
                v-model="quoteForm.dateRequired"
                :format="dateDisplayFormat"
                size="mini"
                type="date"
                :value-format="dateValueFormat"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-divider content-position="left"><h4>Vendor Business Category</h4></el-divider>

      <el-row :gutter="24">
        <el-col :span="20">
          <el-table
            v-loading="loadingCategories"
            ref="vendorInvitation"
            border
            :data="vendorInvitations"
            :default-sort="gridSchema.defaultSort"
            :empty-text="gridSchema.emptyText"
            highlight-current-row
            :max-height="gridSchema.maxHeight"
            size="mini"
            stripe
            style="width: 100%"
          >

            <el-table-column
              label="No"
              width="50"
            >
              <template slot-scope="row">
                {{ row.$index+1 }}
              </template>
            </el-table-column>
            <el-table-column
              label="Business Classification"
              min-width="100"
              prop="businessClassificationName"
            ></el-table-column>
            <el-table-column
              label="Category"
              min-width="100"
              prop="businessCategoryName"
            ></el-table-column>

            <el-table-column
              label="Sub Category"
              min-width="100"
              prop="businessSubCategoryName"
            ></el-table-column>

            <el-table-column
              align="center"
              width="56"
            >
              <template slot="header">
                <el-button
                  size="mini"
                  icon="el-icon-plus"
                  type="primary"
                  @click="addBusinessCategory"
                ></el-button>
              </template>
              <template slot-scope="row">
                <el-button
                  size="mini"
                  icon="el-icon-delete"
                  type="danger"
                  @click="removeBusinessCategory(row.$index)"
                ></el-button>
              </template>
            </el-table-column>

          </el-table>
        </el-col>
      </el-row>

      <el-divider content-position="left"><h4>Vendor Suggestion</h4></el-divider>

      <el-row :gutter="24">
        <el-col :span="20">
          <el-table
            v-loading="loadingSuggestions"
            ref="vendorSuggestion"
            border
            :data="vendorSuggestions"
            :default-sort="gridSchema.defaultSort"
            :empty-text="gridSchema.emptyText"
            highlight-current-row
            :max-height="gridSchema.maxHeight"
            size="mini"
            stripe
            style="width: 100%"
          >

            <el-table-column
              label="No"
              width="50"
            >
              <template slot-scope="row">
                {{ row.$index + 1 }}
              </template>
            </el-table-column>

            <el-table-column
              label="Vendor"
              min-width="200"
              show-overflow-tooltip
            >
              <template slot-scope="{ row }">
                {{ row.vendorName }}
              </template>
            </el-table-column>

            <el-table-column
              label="Sub Category"
              min-width="100"
              show-overflow-tooltip
            >
              <template slot-scope="{ row }">
                {{ row.businessSubCategoryName }}
              </template>
            </el-table-column>

            <!--<el-table-column
              label="Address"
              min-width="250"
              prop="address"
              show-overflow-tooltip
            ></el-table-column>-->

            <el-table-column
              align="center"
              width="56"
            >
              <template slot="header">
                <el-button
                  size="mini"
                  icon="el-icon-plus"
                  type="primary"
                  @click="addVendorSuggestion"
                ></el-button>
              </template>
              <template slot-scope="row">
                <el-button
                  size="mini"
                  icon="el-icon-delete"
                  type="danger"
                  @click="removeVendorSuggestion(row.$index)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <div slot="footer">
        <el-button
          icon="el-icon-check"
          size="mini"
          style="margin-left: 0px;"
          type="primary"
          @click="saveQuotation"
        >
          Save
        </el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="Add Vendor Business Category"
      :visible.sync="vendorInvitationFormVisible"
      width="85%"
    >
      <template>
        <div>
          <el-row :gutter="24">
            <el-col :span="24">
              <el-form
                ref="businessCategoryForm"
                :model="businessCategory"
                :rules="rules"
              >
                <el-form-item prop="values">
                  <el-cascader-panel
                    ref="businessCategories"
                    v-model="businessCategory.values"
                    :options="businessCategoryOptions"
                    :props="{multiple: true}"
                    @change="onBusinessCategoryChanged"
                  ></el-cascader-panel>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
        </div>
        <div slot="footer">
          <el-button
            icon="el-icon-close"
            size="mini"
            style="margin-left: 0px;"
            @click="vendorInvitationFormVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            icon="el-icon-check"
            size="mini"
            style="margin-left: 0px;"
            type="primary"
            @click="saveBusinessCategory"
          >
            Save
          </el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog
      width="50%"
      :visible.sync="dialogConfirmationVisibleVendorSuggestion"
      title="Add Vendor Suggestion"
    >
      <template>
        <div>
          <el-form ref="vendorSuggestion" label-position="left" label-width="150px" size="mini" :model="vendorSuggestion">
            <el-row :gutter="24">
              <el-col :span="24">
                <el-form-item label="Sub Category" prop="subCategory" required>
                  <el-select
                    v-model="vendorSuggestion.subCategory"
                    class="form-input"
                    clearable filterable
                    :placeholder="$t('register.form.select')"
                    style="width: 100%"
                    @change="getVendor($event)"
                    @clear="clearSubCategory"
                  >
                    <el-option
                      v-for="item in subCategoryOptions"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="24">
                <el-form-item label="Vendor" prop="vendor" required>
                  <el-select
                    v-model="vendorSuggestion.vendor"
                    class="form-input"
                    clearable filterable
                    :placeholder="$t('register.form.select')"
                    style="width: 100%"
                    @change="getVendorDetail($event)"
                    @clear="clearVendor"
                  >
                    <el-option
                      v-for="item in vendorOptions"
                      :key="item.vendorId"
                      :label="item.vendorName"
                      :value="item.vendorId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="24">
                <el-form-item label="Address" prop="address" required>
                  <el-input class="form-input" clearable v-model="vendorSuggestion.address" disabled />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div slot="footer">
          <el-button
            icon="el-icon-close"
            size="mini"
            @click="dialogConfirmationVisibleVendorSuggestion = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            icon="el-icon-check"
            size="mini"
            style="margin-left: 0px;"
            type="primary"
            @click="saveVendorSuggestion"
          >
              Save
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./quotation-form.component.ts">
</script>

<style lang="scss">
.el-table__fixed, .el-table__fixed-right {
  box-shadow: none;
}
</style>

<style lang="scss" scoped>
.el-divider--horizontal {
  margin: 12px 0;
}

.form-input {
  width: 100%;
}

.main {
  padding: 0px;
}

.button {
  margin-bottom: 5px;
}
</style>
