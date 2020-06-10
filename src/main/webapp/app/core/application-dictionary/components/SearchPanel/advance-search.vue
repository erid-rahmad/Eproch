<template>
  <div class="advance-search">

    <el-button
      style="margin-left: 0px;"
      type="primary"
      size="mini"
      icon="el-icon-plus"
      plain
      @click="addFilterAdvance"
      title="Add">
      Add
    </el-button>

    <el-button
      style="margin-left: 0px;"
      type="primary"
      size="mini"
      icon="el-icon-search"
      @click="filterAdvance"
      title="Execute">
      Execute
    </el-button>

    <el-button
      style="margin-left: 0px;"
      type="warning"
      size="mini"
      icon="el-icon-refresh"
      plain
      @click="clear"
      title="Clear">
      Clear
    </el-button>

    <el-button 
      style="margin-left: 0px;"
      size="mini"
      type="default" 
      icon="el-icon-close"
      @click="close"
      title="Close"/>

    <el-table
      v-loading="isFetching"
      ref="grid"
      :data="gridData"
      
      size="small"
      style="width: 100%"
      stripe
      :default-sort="gridSchema.defaultSort"
      :empty-text="gridSchema.emptyText"
      fit
      
      @selection-change="handleSelectionChangeFilterAdvance"
    >
      <el-table-column
        fixed
        type="selection"
        align="center"
        width="42">
      </el-table-column>

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
                  value-key="id"
                  placeholder="Select Column"
                  @change="columnOptionType(scope.row, scope.$index)"
              >
                  <el-option
                      v-for="field in gridFields"
                      :key="field.adColumn.name"
                      :label="field.name"
                      :value="field"
                  >
                  <!-- :value="getColumnValue(field.adColumn)" -->
                      <span style="float: left">{{ field.name }}</span>
                      <span style="float: right; color: #8492a6; font-size: 8px">{{ field.adColumn.type }}</span>
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
                      v-for="queryOption in operators(scope.$index)"
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
              
              <el-switch
                v-if="getqueryValueByColumnBoolean(scope.row)"
                v-model="scope.row.queryValue"
              />

              <el-input
                v-else
                class="edit-input"
                size="mini"
                v-model="scope.row.queryValue"
                clearable
              >
              </el-input>

          </template>

      </el-table-column>



      <el-table-column fixed="left" width="66">
        <template slot-scope="scope">
          <el-button
            style="margin-left: 0px;"
            type="danger"
            size="mini"
            icon="el-icon-close"
            plain
            :title="$t('entity.action.delete')"
            @click="removeFilterAdvance(scope.row, scope.$index)"
          />
        </template>
      </el-table-column>
    </el-table>
    
  </div>
</template>
<script lang="ts" src="./advance-search.component.ts"></script>
