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
      <el-aside class="quick-filter">
        <div>
          <span><svg-icon name="icomoo/348-filter"/> Filter</span>
        </div>
        <filter-group
          :list-items="['Laptop', 'Projector', 'Printer']"
          name="mProductCategory"
          title="Category"
          @changed="onFilterCategoryChanged"
        />
        <filter-group
          :list-items="['Dell', 'BENQ', 'Asus', 'Maspion', 'Panasonic']"
          multiple
          name="mBrandName"
          title="Brand"
          @changed="onFilterBrandChanged"
        />
        <filter-group
          name="price"
          title="Price"
          type="range"
          format="number"
          pattern="currency"
          @changed="onFilterPriceChanged"
        />
      </el-aside>
      <el-container>
        <el-header
          class="display-toolbar"
          height="auto"
        >
          <el-col :offset="8" :span="8">
            <el-pagination
              layout="prev, pager, next"
              small
              :total="totalItems"
            />
          </el-col>
          <el-col :span="8">
            <el-form
              inline
              style="text-align: right"
            >
              <el-form-item
                label="Sort by"
                size="mini"
              >
                <el-select v-model="propOrder">
                  <el-option
                    v-for="field in sortableFields"
                    :key="field.label"
                    :label="field.label"
                    :value="field.name"
                  />
                </el-select>
              </el-form-item>
            </el-form>
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
              :xs="24"
              :sm="12"
              :md="8"
              :lg="6"
              :xl="4"
            >
              <el-card
                :body-style="{padding: '0'}"
              >
                <div
                  slot="header"
                  class="product-preview"
                >
                  <img :src="getThumbnailPreview(col)" :alt="col.name"/>
                </div>
                <div class="summary-box">
                  <div class="product-name">{{ col.name }}</div>
                  <div class="product-price">Rp. {{ col.price | formatCurrency }}</div>
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
<style lang="scss">
  .marketplace {
    .display-toolbar {
      padding: 8px 24px 0;
    }

    .quick-filter {
      background: none;
      border-right: 1px solid #dedede;
    }
    .catalog-list {
      padding-top: 4px;
      
      .el-row {
        margin-bottom: 16px;
      }

      .el-card {
        cursor: pointer;
        margin: 0 auto;
        width: 192px;

        .el-card__header {
          padding: 0;
        }

        .product-preview {
          height: 190px;

          img {
            width: 190px;
          }
        }

        .summary-box {
          font-size: 12px;
          padding: 16px;

          .product-name {
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            height: 1.8em;
            line-height: 1.8em;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
          }

          .product-price {
            font-weight: 600;
          }
        }
      }
    }
  }
</style>
