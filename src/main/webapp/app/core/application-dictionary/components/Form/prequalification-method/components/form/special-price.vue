<template>
  <div class="app-container">

    <el-table
      v-loading="listLoading"
      :data="specialPrices"
      fit
      size="mini"
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column
        align="right"
        label="Min"
        style="width: 20%"
      >
        <template slot-scope="{row}">
          <span>{{ row.min }}</span>
        </template>
      </el-table-column>

      <el-table-column
        style="width: 20%"
        align="right"
        label="Max"
      >
        <template slot-scope="{row}">
          <span>{{ row.max }}</span>
        </template>
      </el-table-column>

      <el-table-column
        align="right"
        label="Price"
        style="width: 50%"
      >
        <template slot-scope="{row}">
          <span>{{ row.price }}</span>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        style="width: 10%">
        <template slot="header">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-plus"
            @click="addRow" />
        </template>

        <template slot-scope="{ row, $index }">
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="removeRow(row, $index)" />
        </template>
      </el-table-column>

    </el-table>


    <el-dialog
        width="40%"
        :visible.sync="dialogConfirmationVisible"
        :title="dialogTitle">

        <template>

            <el-row v-if="dialogType=='add'" :gutter="16">
                <el-col :span="24" :offset="0">

                    <el-form
                        ref="formSpecialPrice"
                        label-width="100px"
                        size="mini"
                        :model="form"
                        :rules="rules"
                    >
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Min" prop="min">
                                    <el-input
                                        class="form-input"
                                        v-model="form.min"
                                        required />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Max" prop="max">
                                    <el-input
                                        class="form-input"
                                        v-model="form.max"
                                        required />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Price" prop="price">
                                    <el-input
                                        class="form-input"
                                        v-model="form.price"
                                        required />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row type="flex" justify="end">
                            <el-col :span="11">
                                <el-form-item>
                                    <el-button
                                        style="margin-left: 0px;"
                                        size="mini"
                                        icon="el-icon-check"
                                        type="primary"
                                        @click="saveRow">
                                            Save
                                    </el-button>
                                    <el-button
                                        style="margin-left: 0px;"
                                        size="mini"
                                        icon="el-icon-close"
                                        @click="dialogConfirmationVisible = false">
                                            {{ $t('entity.action.cancel') }}
                                    </el-button>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-form>

                </el-col>
            </el-row>

            <el-row v-else :gutter="16">
                <el-col :span="24" :offset="0">
                    {{ dialogMessage }}
                </el-col>
            </el-row>

            <div slot="footer" v-if="dialogType!='add'">
                <el-button
                    style="margin-left: 0px;"
                    size="mini"
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

<script lang="ts" src="./special-price.component.ts">
</script>

<style lang="scss" scoped>
  .edit-input {
    padding-right: 100px;
  }
  .cancel-btn {
    position: absolute;
    right: 15px;
    top: 10px;
  }
</style>
