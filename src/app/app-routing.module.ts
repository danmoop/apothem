import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './home/home.module#HomePageModule'
  },
  { path: 'authorization', loadChildren: './authorization/authorization.module#AuthorizationPageModule' },
  { path: 'createaccount', loadChildren: './createaccount/createaccount.module#CreateaccountPageModule' },
  { path: 'signin', loadChildren: './signin/signin.module#SigninPageModule' },
  { path: 'edittopics', loadChildren: './edittopics/edittopics.module#EdittopicsPageModule' },
  { path: 'topic/:topic', loadChildren: './topic/topic.module#TopicPageModule' },
  { path: 'create-post', loadChildren: './create-post/create-post.module#CreatePostPageModule' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
