<template>
  <div class="vendor-invitation">
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
            v-if="!readOnly"
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

          <el-table-column
            label="Address"
            min-width="250"
            prop="address"
            show-overflow-tooltip
          ></el-table-column>

          <el-table-column
            v-if="!readOnly"
            align="center"
            width="56"
          >
            <template slot="header">
              <el-button
                size="mini"
                icon="el-icon-plus"
                type="primary"
                :disabled="!canAddVendor"
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
    <el-divider content-position="left">
      <h4>Vendor Qualification</h4>
    </el-divider>
    <prequalification-form :read-only="readOnly"></prequalification-form>

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
                <el-form-item label="SubCategory" prop="subCategory" required>
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

<script lang="ts" src="./vendor-invitation.component.ts"></script>

<style lang="scss">
.compact .vendor-invitation {
  .el-table--mini {
    td, th {
      height: 35px;
    }
  }
}
</style>
