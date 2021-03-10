<template>
  <div class="app-container">
    <el-table
      v-loading="processing"
      ref="gridData"
      highlight-current-row
      border
      stripe
      fit
      size="mini"
      style="width: 100%; height: 100%"
      :height="gridSchema.height"
      :max-height="gridSchema.maxHeight"
      :default-sort="gridSchema.defaultSort"
      :empty-text="gridSchema.emptyText"
      :data="gridData"
      @sort-change="changeOrder"
      @selection-change="onSelectionChanged"
    >
      <el-table-column align="center" fixed type="selection" width="48" />

      <el-table-column fixed width="48">
        <template slot-scope="{ row }">
          <el-link type="primary" size="mini" icon="el-icon-edit" plain :title="$t('entity.action.edit')" @click="editRow(row)" />
        </template>
      </el-table-column>

      <el-table-column min-width="100" sortable label="Image">
        <template slot-scope="{ row }">
          <div class="demo-image__preview">
            <el-image fit="cover" :src="displayImage(row.imgList)" :preview-src-list="displayImageList(row.imgList)">
              <div slot="error" class="image-slot">
                <em class="el-icon-picture-outline"></em>
              </div>
            </el-image>
          </div>
        </template>
      </el-table-column>

      <el-table-column min-width="180" sortable prop="name" label="Name" />
      <el-table-column min-width="100" sortable prop="mbrandName" label="Brand" />
      <el-table-column min-width="180" sortable prop="shortDescription" label="Short Description" />
      <el-table-column min-width="150" sortable prop="mproductCategoryName" label="Product Category" />
      <el-table-column min-width="150" sortable prop="mproductSubCategoryName" label="Sub Category" />
      <el-table-column min-width="100" sortable prop="cuomName" label="UoM" />
      <el-table-column min-width="100" sortable prop="ccurrencyName" label="Currency" />
      <el-table-column min-width="100" sortable prop="price" label="Price">
        <template slot-scope="{ row }">
          {{ row.price | formatCurrency }}
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      ref="pagination"
      background
      layout="sizes, prev, pager, next"
      small
      :current-page.sync="page"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="itemsPerPage"
      :total="queryCount"
      @size-change="changePageSize"
    />
  </div>
</template>

<script lang="ts" src="./catalog-grid.component.ts">
</script>
