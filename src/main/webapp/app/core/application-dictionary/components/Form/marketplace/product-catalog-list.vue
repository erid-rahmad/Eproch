<template>
  <div class="product-catalog-list">
    <el-container v-loading="processing">
      <el-aside class="quick-filter">
        <div>
          <span><svg-icon name="icomoo/348-filter"/> Filter</span>
        </div>
        <filter-group
          :tree-items="categories"
          name="mProductCategory"
          title="Category"
          type="tree"
          @changed="onFilterCategoryChanged"
        ></filter-group>
        <filter-group
          :list-items="brands"
          multiple
          name="mBrandName"
          title="Brand"
          @changed="onFilterBrandChanged"
        ></filter-group>
        <filter-group
          name="price"
          title="Price"
          type="range"
          format="number"
          pattern="currency"
          @changed="onFilterPriceChanged"
        ></filter-group>
        <hr/>
        <el-button
          size="mini"
          style="width: 100%"
          type="primary"
          @click="applyFilter"
        >
          Apply
        </el-button>
      </el-aside>
      <el-container>
        <el-header
          class="display-toolbar"
          height="auto"
        >
          <el-col :offset="6" :span="6">
            <el-pagination
              layout="prev, pager, next"
              small
              :total="totalItems"
            />
          </el-col>
          <el-col :span="12">
            <el-form
              inline
              size="mini"
              style="text-align: right"
            >
              <el-form-item label="Sort by">
                <el-select
                  v-model="propOrder"
                  @change="onSorted"
                >
                  <el-option
                    v-for="field in sortableFields"
                    :key="field.name"
                    :label="field.label"
                    :value="field.name"
                  />
                </el-select>
              </el-form-item>
              <el-form-item style="padding-top: 1px">
                <el-input
                  v-model="filterCriteria.name"
                  clearable
                  placeholder="Search Product"
                >
                  <el-button
                    slot="append"
                    icon="el-icon-search"
                    @click="applyFilter"
                  />
                </el-input>
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
              class="item"
              :xs="24"
              :sm="12"
              :md="8"
              :lg="6"
              :xl="4"
            >
              <el-card
                :body-style="{padding: '0'}"
                @click.native="onItemSelected(col)"
              >
                <div
                  slot="header"
                  class="product-preview"
                >
                  <el-image
                    v-if="!!col.cGallery"
                    :alt="col.name"
                    lazy
                    :src="getThumbnailPreview(col)"
                  />
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
      
      .item {
        padding-bottom: 16px;
        padding-top: 16px;
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
