<template>
  <div class="app-container marketplace">
    <el-row :gutter="24">
      <el-col :span="8">
        <quick-search
          ref="quickSearch"
          :fields="['name', 'mBrandName', 'shortDescription']"
          @completed="onQuickSearchCompleted"
        />
      </el-col>
      <el-col :span="4">
        <shopping-cart
          ref="shoppingCart"
          @canceled="onShoppingCartCanceled"
          @processed="onShoppingCartProcessed"
        />
      </el-col>
    </el-row>
    <el-container v-loading="processing">
      <el-aside>
        <quick-filter>
          <!-- <filter-group
            name="mProductCategory"
            title="Category"
          />
          <filter-group
            multiple
            name="mBrandName"
            title="Brand"
          />
          <filter-group
            name="price"
            title="Price"
            type="range"
          /> -->
        </quick-filter>
      </el-aside>
      <el-container>
        <el-header height="32px">
          <el-col :span="12">

          </el-col>
          <el-col :span="12">

          </el-col>
        </el-header>
        <el-main class="catalog-list">
          <el-row
            v-for="(row, i) in rows"
            :key="`row_${i}`"
            :gutter="16"
            class=""
          >
            <el-col
              v-for="(col, j) in row.columns"
              :key="`row_${i}_col_${j}`"
              :sm="24"
              :md="12"
              :lg="6"
            >
              <el-card shadow="never">
                <div slot="header">
                  <img :src="col.thumbnailSmall" :alt="col.name"/>
                </div>
                <div class="summary-box">
                  <h6>{{ col.name }}</h6>
                  <p>{{ col.shortDescription }}</p>
                  <p>{{ col.price }}</p>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script lang="ts" src="./product-catalog-list.component.ts"></script>
<style lang="scss" scoped>
  .marketplace {
    .catalog-list .el-row {
      margin-bottom: 16px;
    }
  }
</style>
