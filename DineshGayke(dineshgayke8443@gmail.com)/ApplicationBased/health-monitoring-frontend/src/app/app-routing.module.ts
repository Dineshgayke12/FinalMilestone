import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MetricsFormComponent } from './metrics-form/metrics-form.component';
import { UserMetricsComponent } from './user-metrics/user-metrics.component';

const routes: Routes = [
  { path: '', redirectTo: '/metrics-form', pathMatch: 'full' },
  { path: 'metrics-form', component: MetricsFormComponent },
  { path: 'user-metrics', component: UserMetricsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
