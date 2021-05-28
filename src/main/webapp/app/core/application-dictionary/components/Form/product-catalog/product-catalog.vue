<template>
  <div class="app-container card-view product-catalog">
    <div class="toolbar">
      <el-button
        v-if="index"
        class="button"
        style="margin-left: 0px;"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="onClick('add')"
      ></el-button>

      <el-button
        v-if="index"
        class="button"
        style="margin-left: 0px;"
        size="mini"
        type="danger"
        icon="el-icon-delete"
        @click="onClick('remove')"
      ></el-button>

      <el-button
        v-if="!index"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="onFormClosed"
      >
        Close
      </el-button>

      <el-button
        v-if="!index"
        size="mini"
        type="primary"
        @click="onFormSaved"
      >
        <svg-icon name="icomoo/273-checkmark"></svg-icon> Save
      </el-button>

      <el-button
        v-if="index"
        class="button"
        size="mini"
        type="primary"
        icon="el-icon-search"
        @click="onClick('filter')"
      ></el-button>

      <el-button-group v-if="index">
        <el-button
          class="button"
          size="mini"
          type="primary"
          @click="onClick('import')"
        >
          <svg-icon name="icomoo/198-download2"></svg-icon> Import
        </el-button>

        <el-button
          class="button"
          style="margin-left: 0px;"
          size="mini"
          type="primary"
        >
          <svg-icon name="icomoo/199-upload2"></svg-icon> Export
        </el-button>
      </el-button-group>

    </div>

    <div class="card">
      <el-tabs
        v-if="index"
        v-model="activeName"
        type="card"
        @tab-click="onTabClicked"
      >
        <el-tab-pane
          v-for="(item, tabIdx) in tabTitleOptions"
          :key="item.id"
          :label="item.name"
          lazy
          :name="item.value"
        >
          <catalog-grid
            ref="catalogGrid"
            :status="`${tabIdx}`"
            @row-selected="onRowSelected"
            @rows-selected="onRowsSelected"
          ></catalog-grid>
        </el-tab-pane>
      </el-tabs>

      <product-information
        v-else
        ref="productForm"
        :data="setRow"
        @saved="closeProductInformation"
      ></product-information>
    </div>
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
.product-catalog {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;

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

  .form-input {
    width: 100%;
  }

  .toolbar {
    padding: 4px 16px;
  }
}

</style>
