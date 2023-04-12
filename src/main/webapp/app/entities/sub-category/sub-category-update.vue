<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="elonlarApp.subCategory.home.createOrEditLabel"
          data-cy="SubCategoryCreateUpdateHeading"
          v-text="$t('elonlarApp.subCategory.home.createOrEditLabel')"
        >
          Create or edit a SubCategory
        </h2>
        <div>
          <div class="form-group" v-if="subCategory.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="subCategory.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.subCategory.nameUzLat')" for="sub-category-nameUzLat"
              >Name Uz Lat</label
            >
            <input
              type="text"
              class="form-control"
              name="nameUzLat"
              id="sub-category-nameUzLat"
              data-cy="nameUzLat"
              :class="{ valid: !$v.subCategory.nameUzLat.$invalid, invalid: $v.subCategory.nameUzLat.$invalid }"
              v-model="$v.subCategory.nameUzLat.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.subCategory.nameUzKr')" for="sub-category-nameUzKr">Name Uz Kr</label>
            <input
              type="text"
              class="form-control"
              name="nameUzKr"
              id="sub-category-nameUzKr"
              data-cy="nameUzKr"
              :class="{ valid: !$v.subCategory.nameUzKr.$invalid, invalid: $v.subCategory.nameUzKr.$invalid }"
              v-model="$v.subCategory.nameUzKr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.subCategory.nameRu')" for="sub-category-nameRu">Name Ru</label>
            <input
              type="text"
              class="form-control"
              name="nameRu"
              id="sub-category-nameRu"
              data-cy="nameRu"
              :class="{ valid: !$v.subCategory.nameRu.$invalid, invalid: $v.subCategory.nameRu.$invalid }"
              v-model="$v.subCategory.nameRu.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.subCategory.nameEn')" for="sub-category-nameEn">Name En</label>
            <input
              type="text"
              class="form-control"
              name="nameEn"
              id="sub-category-nameEn"
              data-cy="nameEn"
              :class="{ valid: !$v.subCategory.nameEn.$invalid, invalid: $v.subCategory.nameEn.$invalid }"
              v-model="$v.subCategory.nameEn.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.subCategory.category')" for="sub-category-category">Category</label>
            <select class="form-control" id="sub-category-category" data-cy="category" name="category" v-model="subCategory.category">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="subCategory.category && categoryOption.id === subCategory.category.id ? subCategory.category : categoryOption"
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.subCategory.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./sub-category-update.component.ts"></script>
