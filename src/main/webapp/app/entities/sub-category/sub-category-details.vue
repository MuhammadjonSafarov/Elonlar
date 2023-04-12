<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="subCategory">
        <h2 class="jh-entity-heading" data-cy="subCategoryDetailsHeading">
          <span v-text="$t('elonlarApp.subCategory.detail.title')">SubCategory</span> {{ subCategory.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('elonlarApp.subCategory.nameUzLat')">Name Uz Lat</span>
          </dt>
          <dd>
            <span>{{ subCategory.nameUzLat }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.subCategory.nameUzKr')">Name Uz Kr</span>
          </dt>
          <dd>
            <span>{{ subCategory.nameUzKr }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.subCategory.nameRu')">Name Ru</span>
          </dt>
          <dd>
            <span>{{ subCategory.nameRu }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.subCategory.nameEn')">Name En</span>
          </dt>
          <dd>
            <span>{{ subCategory.nameEn }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.subCategory.category')">Category</span>
          </dt>
          <dd>
            <div v-if="subCategory.category">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: subCategory.category.id } }">{{
                subCategory.category.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="subCategory.id"
          :to="{ name: 'SubCategoryEdit', params: { subCategoryId: subCategory.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./sub-category-details.component.ts"></script>
